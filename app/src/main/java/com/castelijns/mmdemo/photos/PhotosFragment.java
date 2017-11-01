package com.castelijns.mmdemo.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;
import com.castelijns.mmdemo.models.Photo;
import com.castelijns.mmdemo.photo_detail.PhotoDetailActivity;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class PhotosFragment extends BaseFragment implements PhotosContract.View {

    public static final int PHOTOS_GRID_COLS = 5;

    public static final String EXTRA_PHOTO = "extra_photo";
    public static final String EXTRA_PHOTO_TRANSITION = "extra_photo_transition";

    @BindView(R.id.tv_header)
    TextView tvHeader;

    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    private PhotosContract.Presenter presenter;

    private SectionedRecyclerViewAdapter sectionAdapter;
    private int itemWidth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_filter).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    public void filterActionClicked() {
        View view = getLayoutInflater().inflate(R.layout.dialog_input, null);
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.filter_albums_title)
                .setMessage(R.string.filter_albums_message)
                .setView(view)
                .setPositiveButton(R.string.filter, (dialog, which) -> {
                    int albumId = Integer.parseInt(((EditText) view.findViewById(R.id.et_input))
                            .getText().toString());
                    presenter.filterAlbums(albumId);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showPhotos(SparseArray<List<Photo>> albumPhotos) {
        sectionAdapter.removeAllSections();

        for (int i = 0; i < albumPhotos.size(); i++) {
            PhotoSection section = new PhotoSection(getActivity(), albumPhotos.keyAt(i),
                    albumPhotos.valueAt(i), itemWidth);
            section.setItemClickListener((photo, ivPhoto) -> presenter.onPhotoClicked(
                    photo, ivPhoto));

            sectionAdapter.addSection(section);
        }

        rvPhotos.setAdapter(sectionAdapter);
    }

    @Override
    public void showPhotoCount(int photoCount, int albumCount) {
        tvHeader.setText(String.format(Locale.getDefault(),
                "%d %s in %d %s", photoCount, getString(R.string.header_photos), albumCount,
                getString(R.string.header_albums)));
    }

    @Override
    public void showPhotoDetail(Photo photo, ImageView ivPhoto) {
        String transitionName = ViewCompat.getTransitionName(ivPhoto);

        Intent intent = new Intent(getContext(), PhotoDetailActivity.class);
        intent.putExtra(EXTRA_PHOTO, photo);
        intent.putExtra(EXTRA_PHOTO_TRANSITION, transitionName);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), ivPhoto, transitionName);

        startActivity(intent, options.toBundle());
    }
}
