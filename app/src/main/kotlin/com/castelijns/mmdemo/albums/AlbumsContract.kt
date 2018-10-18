package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.app.BaseView
import com.castelijns.mmdemo.models.Album

interface AlbumsContract {

    interface View : BaseView, BaseListView {
        fun showAlbums(albums: List<Album>)
        fun showAlbumCount(count: Int)
    }

    interface Presenter : BasePresenter
}
