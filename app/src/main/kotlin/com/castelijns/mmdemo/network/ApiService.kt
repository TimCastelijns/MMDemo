package com.castelijns.mmdemo.network

import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.models.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("albums")
    fun getAllAlbums(): Deferred<List<Album>>

    @GET("photos")
    fun getAllPhotos(): Deferred<List<Photo>>

    @GET("users")
    fun getAllUsers(): Deferred<List<User>>

    @GET("albums")
    fun getAlbumsById(@Query("id") albumId: Int): Deferred<List<Album>>

    @GET("photos")
    fun getAllPhotosForAlbumId(@Query("albumId") albumId: Int): Deferred<List<Photo>>

}
