package com.castelijns.mmdemo.network;

import com.castelijns.mmdemo.models.Album;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("albums")
    Observable<List<Album>> getAllAlbums();
}
