package com.castelijns.mmdemo.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;
import com.castelijns.mmdemo.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PhotosFragment extends BaseFragment implements PhotosContract.View {

    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    private PhotosContract.Presenter presenter;

    private PhotosAdapter adapter;
    private List<Photo> photos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PhotosPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photos = new ArrayList<>();
        adapter = new PhotosAdapter(getContext(), photos);
        rvPhotos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setAdapter(adapter);

        presenter.start();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        this.photos.addAll(photos);
        adapter.notifyDataSetChanged();
    }
}
