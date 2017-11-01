package com.castelijns.mmdemo.photos;

import android.util.SparseArray;
import android.widget.ImageView;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;
import com.castelijns.mmdemo.models.Photo;

import java.util.List;

public interface PhotosContract {

    interface View extends BaseView {
        void showPhotos(SparseArray<List<Photo>> albumPhotos);
        void showPhotoCount(int photoCount, int albumCount);
        void showPhotoDetail(Photo photo, ImageView ivPhoto);
    }

    interface Presenter extends BasePresenter {
        void onPhotoClicked(Photo photo, ImageView ivPhoto);
    }
}
