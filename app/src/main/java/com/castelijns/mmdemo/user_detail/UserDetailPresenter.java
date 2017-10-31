package com.castelijns.mmdemo.user_detail;

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private UserDetailContract.View view;

    UserDetailPresenter(UserDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
