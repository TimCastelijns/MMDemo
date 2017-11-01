package com.castelijns.mmdemo.photos;

import android.util.SparseArray;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;
import com.castelijns.mmdemo.models.Photo;

import java.util.List;

public interface PhotosContract {

    interface View extends BaseView {
        void showPhotos(SparseArray<List<Photo>> albumPhotos);
    }

    interface Presenter extends BasePresenter {

    }
}
