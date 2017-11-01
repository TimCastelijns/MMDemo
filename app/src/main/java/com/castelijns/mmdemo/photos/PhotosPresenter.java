package com.castelijns.mmdemo.photos;

import android.util.SparseArray;
import android.widget.ImageView;

import com.castelijns.mmdemo.models.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotosPresenter implements PhotosContract.Presenter {

    private PhotosContract.View view;
    private PhotosRepo repo;

    PhotosPresenter(PhotosContract.View view) {
        this.view = view;
        repo = PhotosRepo.getInstance();
    }

    @Override
    public void start() {
        repo.getAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(photos -> {
                    // Split albums by albumId.
                    SparseArray<List<Photo>> albumPhotos = new SparseArray<>();
                    for (Photo photo : photos) {
                        List<Photo> photoList = albumPhotos.get(photo.getAlbumId(), new ArrayList<>());
                        photoList.add(photo);

                        albumPhotos.append(photo.getAlbumId(), photoList);
                    }
                    return albumPhotos;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SparseArray<List<Photo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SparseArray<List<Photo>> albumPhotos) {
                        view.showPhotos(albumPhotos);

                        int photoCount = 0;
                        for (int i = 0; i < albumPhotos.size(); i++) {
                            photoCount += albumPhotos.valueAt(i).size();
                        }
                        view.showPhotoCount(photoCount, albumPhotos.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void stop() {

    }

    @Override
    public void onPhotoClicked(Photo photo, ImageView ivPhoto) {
        view.showPhotoDetail(photo, ivPhoto);
    }
}
