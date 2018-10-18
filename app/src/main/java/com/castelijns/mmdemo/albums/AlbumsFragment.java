package com.castelijns.mmdemo.albums;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseListFragment;
import com.castelijns.mmdemo.models.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class AlbumsFragment extends BaseListFragment implements AlbumsContract.View {

    @BindView(R.id.rv_albums)
    RecyclerView rvAlbums;

    private AlbumsContract.Presenter presenter;

    private AlbumsAdapter adapter;
    private List<Album> albums;
    private AlbumsAdapter.ItemClickListener itemClickListener;

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
        adapter.setItemClickListener(itemClickListener);
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAlbums.setHasFixedSize(true);
        rvAlbums.setAdapter(adapter);

        presenter.start();
    }

    @Override
    public void onDestroyView() {
        presenter.stop();
        super.onDestroyView();
    }

    public void setShowPhotosClickedListener(AlbumsAdapter.ItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public void showAlbums(List<Album> albums) {
        this.albums.addAll(albums);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumCount(int count) {
         setHeaderText(String.format(Locale.getDefault(), "%d %s", count, getString(R.string.header_albums)));
    }
}
