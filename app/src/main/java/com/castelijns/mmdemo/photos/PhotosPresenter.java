package com.castelijns.mmdemo.photos;

import com.castelijns.mmdemo.models.Photo;

import java.util.List;

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Photo> photos) {

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
}
