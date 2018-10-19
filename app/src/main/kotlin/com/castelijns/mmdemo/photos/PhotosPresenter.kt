package com.castelijns.mmdemo.photos

import android.widget.ImageView
import com.castelijns.mmdemo.albums.AlbumsRepo
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo
import kotlinx.coroutines.experimental.launch

class PhotosPresenter(
        private val view: PhotosContract.View
) : BasePresenter() {

    private val photosRepo = PhotosRepo
    private val albumsRepo = AlbumsRepo

    override fun start() {
        view.clearList()
        view.showLoading()

        launch {
            try {
                loadPhotos()
            } catch (e: Exception) {
                view.showError()
            } finally {
                view.hideLoading()
            }
        }
    }

    private suspend fun loadPhotos() {
        val allPhotosJob = photosRepo.getAllPhotos()
        val allAlbumsJob = albumsRepo.getAllAlbums()

        val photos = allPhotosJob.await()
        val albums = allAlbumsJob.await()

        // Assign album titles to photos
        photos.forEach { photo ->
            photo.albumTitle = albums.first { album -> album.id == photo.albumId }.title
        }

        // Split photos by albumId
        val photosByAlbum: Map<Album, List<Photo>> = photos.groupBy { photo ->
            albums.first { album -> album.id == photo.albumId }
        }

        view.showPhotosByAlbum(photosByAlbum)
        view.showPhotoCount(photos.size, photosByAlbum.keys.size)

        photosRepo.cacheData(photos)
        albumsRepo.cacheData(albums)
    }

    fun start(albumId: Int) {
        view.clearList()
        view.showLoading()

        launch {
            try {
                loadPhotosForAlbum(albumId)
            } catch (e: Exception) {
                view.showError()
            } finally {
                view.hideLoading()
            }
        }
    }

    fun onPhotoClicked(photo: Photo, ivPhoto: ImageView) {
        view.showPhotoDetail(photo, ivPhoto)
    }

    private suspend fun loadPhotosForAlbum(albumId: Int) {
        val photosJob = photosRepo.getAllPhotosForAlbumId(albumId)
        val albumJob = albumsRepo.getAlbumById(albumId)

        val photos = photosJob.await()
        val album = albumJob.await()

        // Assign album title to photos
        photos.forEach { photo ->
            photo.albumTitle = album.title
        }

        if (photos.isNotEmpty()) {
            view.showPhotosByAlbum(mapOf(album to photos))
            view.showPhotoCount(photos.size, 1)
        } else {
            view.showPhotosByAlbum(mapOf())
            view.showPhotoCount(0, 0)
        }
    }

}
