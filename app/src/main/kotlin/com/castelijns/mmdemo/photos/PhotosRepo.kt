package com.castelijns.mmdemo.photos

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.network.ApiManager
import com.castelijns.mmdemo.network.ApiService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

object PhotosRepo : SimpleCache<Photo>() {

    private var apiService: ApiService = ApiManager.service

    suspend fun getAllPhotos(): Deferred<List<Photo>> = coroutineScope {
        async(Dispatchers.IO) {
            cache ?: apiService.getAllPhotos().await()
        }
    }

    suspend fun getAllPhotosForAlbumId(albumId: Int): Deferred<List<Photo>> =
            coroutineScope {
                async {
                    cache?.filter { photo ->
                        photo.albumId == albumId
                    } ?: apiService.getAllPhotosForAlbumId(albumId).await()
                }
            }

}