package com.castelijns.mmdemo.albums;


import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.network.ApiManager;
import com.castelijns.mmdemo.network.ApiService;

import java.util.List;

import io.reactivex.Observable;

class AlbumsRepo {

    private static AlbumsRepo instance = null;

    private static ApiService apiService;

    private AlbumsRepo() {
        apiService = ApiManager.getService();
    }

    static AlbumsRepo getInstance() {
        if (instance == null) {
            instance = new AlbumsRepo();
        }

        return instance;
    }

    Observable<List<Album>> getAllAlbums() {
        return apiService.getAllAlbums();
    }
}
