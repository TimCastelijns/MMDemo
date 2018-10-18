package com.castelijns.mmdemo.photos

import android.util.SparseArray
import android.widget.ImageView

import com.castelijns.mmdemo.albums.AlbumsRepo
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class PhotosPresenter internal constructor(private val view: PhotosContract.View) : PhotosContract.Presenter {
    private val photosRepo = PhotosRepo
    private val albumsRepo = AlbumsRepo

    private var disposable: Disposable? = null


    override fun start() {
        view.clearList()
        view.showLoading()

        disposable = Observable.zip<List<Photo>, List<Album>, Pair>(photosRepo.allPhotos, albumsRepo.getAllAlbums(),
                BiFunction { photos, albums ->
                    val pair = Pair()
                    pair.photos = photos
                    pair.albums = albums
                    pair
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { pair ->
                    // Assign album titles to photos;
                    pair.photos?.forEach { photo ->
                        pair.albums?.forEach { album ->
                            if (album.id == photo.albumId) {
                                photo.albumTitle = album.title
                            }
                        }
                    }

                    pair
                }
                .doOnNext { pair ->
                    photosRepo.cacheData(pair.photos!!)
                    albumsRepo.cacheData(pair.albums!!)
                }
                .map { pair ->
                    // Split albums by albumId.
                    val albumPhotos = SparseArray<MutableList<Photo>>()
                    for (photo in pair.photos!!) {
                        val photoList = albumPhotos.get(photo.albumId, ArrayList())
                        photoList.add(photo )

                        albumPhotos.append(photo.albumId, photoList)
                    }
                    albumPhotos
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val albumPhotos = SparseArray<List<Photo>>()
                    for (i in 0 until it.size()) {
                        albumPhotos.append(it.keyAt(i), it.valueAt(i))
                    }
                    view.showPhotos(albumPhotos)

                    var photoCount = 0
                    for (i in 0 until albumPhotos.size()) {
                        photoCount += albumPhotos.valueAt(i).size
                    }
                    view.showPhotoCount(photoCount, albumPhotos.size())
                }, {
                    view.hideLoading()
                    view.showError()
                }, {
                    view.hideLoading()
                })
    }

    override fun start(albumId: Int) {
        filterAlbums(albumId)
    }

    override fun stop() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onPhotoClicked(photo: Photo, ivPhoto: ImageView) {
        view.showPhotoDetail(photo, ivPhoto)
    }

    override fun filterAlbums(albumId: Int) {
        view.clearList()
        view.showLoading()

        disposable = Observable.zip<List<Photo>, Album, Pair>(photosRepo.getAllPhotosForAlbumId(albumId), albumsRepo.getAlbumById(albumId),
                BiFunction { photos, album ->
                    val pair = Pair()
                    pair.photos = photos
                    pair.album = album
                    pair
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { pair ->
                    // Assign album title to photos;
                    for (photo in pair.photos!!) {
                        photo.albumTitle = pair.album!!.title
                    }

                    pair
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({pair ->
                    if (pair.photos!!.isNotEmpty()) {
                        val albumPhotos = SparseArray<List<Photo>>()
                        albumPhotos.append(pair.photos!![0].albumId, pair.photos)

                        view.showPhotos(albumPhotos)
                        view.showPhotoCount(pair.photos!!.size, 1)
                    } else {
                        view.showPhotos(SparseArray())
                        view.showPhotoCount(0, 0)
                    }
                }, {
                    view.hideLoading()
                    view.showError()
                }, {
                    view.hideLoading()
                })
    }

    private inner class Pair {
        internal var photos: List<Photo>? = null
        internal var albums: List<Album>? = null

        internal var album: Album? = null // Only in case of filter.
    }
}
