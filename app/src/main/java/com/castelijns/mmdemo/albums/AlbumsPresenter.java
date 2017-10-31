package com.castelijns.mmdemo.albums;

import com.castelijns.mmdemo.models.Album;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumsPresenter implements AlbumsContract.Presenter {

    private AlbumsContract.View view;
    private AlbumsRepo repo;

    AlbumsPresenter(AlbumsContract.View view) {
        this.view = view;
        repo = AlbumsRepo.getInstance();
    }

    @Override
    public void start() {
        repo.getAllAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Album> albums) {

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
