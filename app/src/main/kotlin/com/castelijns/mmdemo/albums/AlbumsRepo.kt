package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.network.ApiManager
import com.castelijns.mmdemo.network.ApiService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

object AlbumsRepo : SimpleCache<Album>() {

    private var apiService: ApiService = ApiManager.service

    suspend fun getAllAlbums(): Deferred<List<Album>> = coroutineScope {
        async(Dispatchers.Main) {
            cache ?: apiService.getAllAlbums().await()
        }
    }

    suspend fun getAlbumById(albumId: Int): Deferred<Album> = coroutineScope {
        async(Dispatchers.IO) {
            val albums = apiService.getAlbumsById(albumId).await()
            albums.first()
        }
    }

}
