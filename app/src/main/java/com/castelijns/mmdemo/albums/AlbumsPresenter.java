package com.castelijns.mmdemo.albums;

public class AlbumsPresenter implements AlbumsContract.Presenter {

    private AlbumsContract.View view;

    AlbumsPresenter(AlbumsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
