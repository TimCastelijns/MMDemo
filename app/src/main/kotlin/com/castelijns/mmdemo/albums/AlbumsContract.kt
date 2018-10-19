package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.models.Album

interface AlbumsContract {

    interface View : BaseListView {
        fun showAlbums(albums: List<Album>)
        fun showAlbumCount(count: Int)
    }

}
