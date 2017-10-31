package com.castelijns.mmdemo.users;

public class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View view;

    UsersPresenter(UsersContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
