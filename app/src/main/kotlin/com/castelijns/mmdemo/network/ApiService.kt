package com.castelijns.mmdemo.network

import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.models.User

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("albums")
    fun getAllAlbums(): Observable<List<Album>>

    @GET("photos")
    fun getAllPhotos(): Observable<List<Photo>>

    @GET("users")
    fun getAllUsers(): Observable<List<User>>

    @GET("albums")
    fun getAlbumsById(@Query("id") albumId: Int): Observable<List<Album>>

    @GET("photos")
    fun getAllPhotosForAlbumId(@Query("albumId") albumId: Int): Observable<List<Photo>>

}
