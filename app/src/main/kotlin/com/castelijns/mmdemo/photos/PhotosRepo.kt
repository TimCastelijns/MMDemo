package com.castelijns.mmdemo.photos

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.network.ApiManager
import com.castelijns.mmdemo.network.ApiService
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

object PhotosRepo : SimpleCache<Photo>() {

    private var apiService: ApiService = ApiManager.service

    suspend fun getAllPhotos(): Deferred<List<Photo>> = coroutineScope {
        async { emptyList<Photo>() }
    }
//        get() = if (cache != null) {
//            Observable.just(cache)
//        } else {
//            apiService.getAllPhotos()
//        }

    suspend fun getAllPhotosForAlbumId(albumId: Int): Deferred<List<Photo>> =
            coroutineScope {
                async { emptyList<Photo>() }
//                async {
//                    apiService.getAllPhotosForAlbumId(albumId)
//                }
            }

}