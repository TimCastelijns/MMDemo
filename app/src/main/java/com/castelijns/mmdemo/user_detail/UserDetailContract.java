package com.castelijns.mmdemo.user_detail;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;

public interface UserDetailContract {

    interface View extends BaseView {
        void showNavigationTo(String lat, String lon);
    }

    interface Presenter extends BasePresenter {
        void onDirectionsClicked();
    }
}
