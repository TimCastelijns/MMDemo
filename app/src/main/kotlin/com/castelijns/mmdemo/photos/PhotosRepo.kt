package com.castelijns.mmdemo.photos

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.network.ApiManager
import com.castelijns.mmdemo.network.ApiService
import io.reactivex.Observable

object PhotosRepo : SimpleCache<Photo>() {

    private var apiService: ApiService = ApiManager.service

    val allPhotos: Observable<List<Photo>>
        get() = if (cache != null) {
            Observable.just(cache)
        } else {
            apiService.getAllPhotos()
        }

    fun getAllPhotosForAlbumId(albumId: Int): Observable<List<Photo>> =
            apiService.getAllPhotosForAlbumId(albumId)

}