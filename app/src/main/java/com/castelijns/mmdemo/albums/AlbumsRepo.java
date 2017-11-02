package com.castelijns.mmdemo.albums;

import com.castelijns.mmdemo.app.SimpleCache;
import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.network.ApiManager;
import com.castelijns.mmdemo.network.ApiService;

import java.util.List;

import io.reactivex.Observable;

public class AlbumsRepo extends SimpleCache<Album> {

    private static AlbumsRepo instance = null;

    private static ApiService apiService;

    private AlbumsRepo() {
        apiService = ApiManager.getService();
    }

    public static AlbumsRepo getInstance() {
        if (instance == null) {
            instance = new AlbumsRepo();
        }

        return instance;
    }

    public Observable<Album> getAlbumById(int albumId) {
        return apiService.getAlbumsById(albumId).map(albums -> albums.get(0));
    }

    public Observable<List<Album>> getAllAlbums() {
        if (cache != null) {
            return Observable.just(cache);
        } else {
            return apiService.getAllAlbums();
        }
    }
}
