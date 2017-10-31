package com.castelijns.mmdemo.network;

import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.users.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("albums")
    Observable<List<Album>> getAllAlbums();

    @GET("users")
    Observable<List<User>> getAllUsers();
}
