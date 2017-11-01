package com.castelijns.mmdemo.photo_detail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseActivity;
import com.castelijns.mmdemo.models.Photo;
import com.castelijns.mmdemo.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.castelijns.mmdemo.photos.PhotosFragment.EXTRA_PHOTO;
import static com.castelijns.mmdemo.photos.PhotosFragment.EXTRA_PHOTO_TRANSITION;
import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_EMAIL_TRANSITION;
import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_USER;
import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_USERNAME_TRANSITION;

public class PhotoDetailActivity extends BaseActivity {

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);

        Photo photo= getIntent().getParcelableExtra(EXTRA_PHOTO);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(photo.getTitle());

        getWindow().setStatusBarColor(Color.BLACK);

        // For shared element transitions.
        supportPostponeEnterTransition();

        ivPhoto.setTransitionName(getIntent().getStringExtra(EXTRA_PHOTO_TRANSITION));
        Glide.with(this)
                .load(photo.getUrl())
                .into(ivPhoto);

        supportStartPostponedEnterTransition();
        // end.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
