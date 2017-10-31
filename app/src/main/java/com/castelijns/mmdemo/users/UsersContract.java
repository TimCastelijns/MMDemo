package com.castelijns.mmdemo.users;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;

import java.util.List;

public interface UsersContract {

    interface View extends BaseView {
        void showUsers(List<User> users);
    }

    interface Presenter extends BasePresenter {

    }
}