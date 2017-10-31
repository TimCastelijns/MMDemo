package com.castelijns.mmdemo.albums;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;
import com.castelijns.mmdemo.models.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AlbumsFragment extends BaseFragment implements AlbumsContract.View {

    @BindView(R.id.rv_albums)
    RecyclerView rvAlbums;

    private AlbumsContract.Presenter presenter;

    private AlbumsAdapter adapter;
    private List<Album> albums;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AlbumsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albums = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext(), albums);
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAlbums.setHasFixedSize(true);
        rvAlbums.setAdapter(adapter);

        presenter.start();
    }

    @Override
    public void showAlbums(List<Album> albums) {
        this.albums.addAll(albums);
        adapter.notifyDataSetChanged();
    }
}
