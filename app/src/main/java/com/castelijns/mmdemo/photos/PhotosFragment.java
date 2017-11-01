package com.castelijns.mmdemo.photos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;
import com.castelijns.mmdemo.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class PhotosFragment extends BaseFragment implements PhotosContract.View {

    public static final int PHOTOS_GRID_COLS = 5;

    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    private PhotosContract.Presenter presenter;

    private SectionedRecyclerViewAdapter sectionAdapter;
    private int itemWidth;
    private PhotosAdapter adapter;
    private List<Photo> photos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Calculate how wide one col is allowed to be.
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        itemWidth = displaymetrics.widthPixels / PHOTOS_GRID_COLS;

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

        sectionAdapter = new SectionedRecyclerViewAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), PHOTOS_GRID_COLS);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return PHOTOS_GRID_COLS;
                    default:
                        return 1;
                }
            }
        });

        rvPhotos.setLayoutManager(gridLayoutManager);

        presenter.start();
    }

    @Override
    public void showPhotos(SparseArray<List<Photo>> albumPhotos) {
        for (int i = 0; i < albumPhotos.size(); i++) {
            sectionAdapter.addSection(new PhotoSection(getActivity(), albumPhotos.keyAt(i),
                    albumPhotos.valueAt(i), itemWidth));
        }

        rvPhotos.setAdapter(sectionAdapter);
    }
}
