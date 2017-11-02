package com.castelijns.mmdemo.network;

import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.models.Photo;
import com.castelijns.mmdemo.models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("albums")
    Observable<List<Album>> getAlbumsById(@Query("id") int albumId);

    @GET("albums")
    Observable<List<Album>> getAllAlbums();

    @GET("photos")
    Observable<List<Photo>> getAllPhotos();

    @GET("photos")
    Observable<List<Photo>> getAllPhotosForAlbumId(@Query("albumId") int albumId);

    @GET("users")
    Observable<List<User>> getAllUsers();
}
