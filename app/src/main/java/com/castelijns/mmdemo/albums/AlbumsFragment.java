package com.castelijns.mmdemo.albums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;

public class AlbumsFragment extends BaseFragment implements AlbumsContract.View {

    private AlbumsContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AlbumsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        presenter.start();

        return view;
    }
}
