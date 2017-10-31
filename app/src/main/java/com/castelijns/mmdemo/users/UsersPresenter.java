package com.castelijns.mmdemo.users;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View view;
    private UsersRepo repo;

    UsersPresenter(UsersContract.View view) {
        this.view = view;
        repo = UsersRepo.getInstance();
    }

    @Override
    public void start() {
        repo.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> users) {

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
