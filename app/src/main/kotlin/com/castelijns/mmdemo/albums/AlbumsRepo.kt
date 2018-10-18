package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.network.ApiManager
import com.castelijns.mmdemo.network.ApiService

import io.reactivex.Observable

object AlbumsRepo : SimpleCache<Album>() {

    private var apiService: ApiService = ApiManager.service

    fun getAllAlbums(): Observable<List<Album>> =
            if (cache != null) {
                Observable.just(cache)
            } else {
                apiService.getAllAlbums()
            }

    fun getAlbumById(albumId: Int): Observable<Album> =
            apiService.getAlbumsById(albumId).map { albums -> albums[0] }

}
