package com.castelijns.mmdemo.photos

import android.widget.ImageView
import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo

interface PhotosContract {

    interface View :  BaseListView {
        fun clearList()
        fun showPhotosByAlbum(photosByAlbum: Map<Album, List<Photo>>)
        fun showPhotoCount(photoCount: Int, albumCount: Int)
        fun showPhotoDetail(photo: Photo, ivPhoto: ImageView)
    }

}
