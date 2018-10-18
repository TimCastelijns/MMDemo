package com.castelijns.mmdemo.photos

import android.util.SparseArray
import android.widget.ImageView

import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.app.BaseView
import com.castelijns.mmdemo.models.Photo

interface PhotosContract {

    interface View : BaseView, BaseListView {
        fun clearList()
        fun showPhotos(albumPhotos: SparseArray<List<Photo>>)
        fun showPhotoCount(photoCount: Int, albumCount: Int)
        fun showPhotoDetail(photo: Photo, ivPhoto: ImageView)
    }

}
