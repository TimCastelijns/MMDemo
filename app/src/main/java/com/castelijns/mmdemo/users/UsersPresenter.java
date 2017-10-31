package com.castelijns.mmdemo.users;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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
                        Collections.sort(users);
                        view.showUsers(users);
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
