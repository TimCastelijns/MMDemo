package com.castelijns.mmdemo.user_detail;

import com.castelijns.mmdemo.models.User;

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private UserDetailContract.View view;
    private User user;

    UserDetailPresenter(UserDetailContract.View view, User user) {
        this.view = view;
        this.user = user;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onDirectionsClicked() {
        view.showNavigationTo(user.getAddress().getGeo().get("lat"),
                user.getAddress().getGeo().get("lon"));
    }
}
