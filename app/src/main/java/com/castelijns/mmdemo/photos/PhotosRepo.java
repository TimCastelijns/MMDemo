package com.castelijns.mmdemo.photos;

import com.castelijns.mmdemo.app.SimpleCache;
import com.castelijns.mmdemo.models.Photo;
import com.castelijns.mmdemo.network.ApiManager;
import com.castelijns.mmdemo.network.ApiService;

import java.util.List;

import io.reactivex.Observable;

class PhotosRepo extends SimpleCache<Photo> {

    private static PhotosRepo instance = null;

    private static ApiService apiService;

    private PhotosRepo() {
        apiService = ApiManager.getService();
    }

    static PhotosRepo getInstance() {
        if (instance == null) {
            instance = new PhotosRepo();
        }

        return instance;
    }

    Observable<List<Photo>> getAllPhotos() {
        if (cache != null) {
            return Observable.just(cache);
        } else {
            return apiService.getAllPhotos();
        }
    }

    Observable<List<Photo>> getAllPhotosForAlbumId(int albumId) {
        return apiService.getAllPhotosForAlbumId(albumId);
    }
}