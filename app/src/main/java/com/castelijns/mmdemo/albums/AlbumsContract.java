package com.castelijns.mmdemo.albums;

import com.castelijns.mmdemo.app.BasePresenter;
import com.castelijns.mmdemo.app.BaseView;
import com.castelijns.mmdemo.models.Album;

import java.util.List;

public interface AlbumsContract {

    interface View extends BaseView {
        void showAlbums(List<Album> albums);
        void showAlbumCount(int count);
    }

    interface Presenter extends BasePresenter {

    }
}
