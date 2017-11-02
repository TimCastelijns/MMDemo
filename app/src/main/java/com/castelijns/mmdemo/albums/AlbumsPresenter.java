package com.castelijns.mmdemo.albums;

import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.models.User;
import com.castelijns.mmdemo.users.UsersRepo;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumsPresenter implements AlbumsContract.Presenter {

    private AlbumsContract.View view;
    private AlbumsRepo albumsRepo;
    private UsersRepo usersRepo;

    private Disposable disposable;

    AlbumsPresenter(AlbumsContract.View view) {
        this.view = view;
        albumsRepo = AlbumsRepo.getInstance();
        usersRepo = UsersRepo.getInstance();
    }

    @Override
    public void start() {
        view.showLoading();
        Observable.zip(albumsRepo.getAllAlbums(), usersRepo.getAllUsers(), (albums, users) -> {
            Pair pair = new Pair();
            pair.albums = albums;
            pair.users = users;
            return pair;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(pair -> {
                    Collections.sort(pair.albums);

                    // Assign user names to albums.
                    for (Album album : pair.albums) {
                        for (User user : pair.users) {
                            if (user.getId() == album.getUserId()) {
                                album.setUserName(user.getName());
                            }
                        }
                    }

                    return pair;
                })
                .doOnNext(pair -> {
                    albumsRepo.cacheData(pair.albums);
                    usersRepo.cacheData(pair.users);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Pair pair) {
                        view.showAlbums(pair.albums);
                        view.showAlbumCount(pair.albums.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void stop() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private class Pair {
        List<Album> albums;
        List<User> users;
    }
}
