package com.castelijns.mmdemo.users;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;
import com.castelijns.mmdemo.models.User;

import java.util.List;

public interface UsersContract {

    interface View extends BaseView {
        void showUsers(List<User> users);
        void showUserDetail(User user);
    }

    interface Presenter extends BasePresenter {
        void onUserClicked(User user);
    }
}