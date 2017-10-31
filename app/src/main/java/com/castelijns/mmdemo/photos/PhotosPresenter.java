package com.castelijns.mmdemo.photos;

public class PhotosPresenter implements PhotosContract.Presenter {

    private PhotosContract.View view;

    PhotosPresenter(PhotosContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
