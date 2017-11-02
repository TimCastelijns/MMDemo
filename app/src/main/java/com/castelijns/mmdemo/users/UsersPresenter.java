package com.castelijns.mmdemo.users;

import android.widget.TextView;

import com.castelijns.mmdemo.models.User;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View view;
    private UsersRepo repo;

    private Disposable disposable;

    UsersPresenter(UsersContract.View view) {
        this.view = view;
        repo = UsersRepo.getInstance();
    }

    @Override
    public void start() {
        repo.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext(users -> repo.cacheData(users))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<User> users) {
                        Collections.sort(users);
                        view.showUsers(users);
                        view.showUserCount(users.size());
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
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onUserClicked(User user, TextView tvUsername, TextView tvEmail) {
        this.view.showUserDetail(user, tvUsername, tvEmail);
    }
}
