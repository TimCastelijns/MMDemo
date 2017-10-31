package com.castelijns.mmdemo.photos;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;
import com.castelijns.mmdemo.models.Photo;

import java.util.List;

public interface PhotosContract {

    interface View extends BaseView {
        void showPhotos(List<Photo> photos);
    }

    interface Presenter extends BasePresenter {

    }
}
