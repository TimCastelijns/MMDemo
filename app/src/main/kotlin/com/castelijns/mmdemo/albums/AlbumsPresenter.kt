package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.users.UsersRepo
import kotlinx.coroutines.experimental.launch

class AlbumsPresenter(
        private val view: AlbumsContract.View
) : BasePresenter() {

    private val albumsRepo = AlbumsRepo
    private val usersRepo = UsersRepo

    override fun start() {
        view.showLoading()

        launch {
            try {
                loadAlbums()
            } catch (e: Exception) {
                view.showError()
            } finally {
                view.hideLoading()
            }
        }
    }

    private suspend fun loadAlbums() {
        val allAlbumsJob = albumsRepo.getAllAlbums()
        val allUsersJob = usersRepo.getAllUsers()

        var albums = allAlbumsJob.await()
        val users = allUsersJob.await()

        // Assign user names to albums.
        albums.forEach { album ->
            album.userName = users.first { user -> user.id == album.userId }.name
        }

        // Sort alphabetically.
        albums = albums.sortedBy { it.title }

        albumsRepo.cacheData(albums)
        usersRepo.cacheData(users)

        view.showAlbums(albums)
        view.showAlbumCount(albums.size)
    }

}
