package com.castelijns.mmdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.castelijns.mmdemo.albums.AlbumsFragment;
import com.castelijns.mmdemo.app.BaseActivity;
import com.castelijns.mmdemo.photos.PhotosFragment;
import com.castelijns.mmdemo.users.UsersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    private AlbumsFragment albumsFragment;
    private PhotosFragment photosFragment;
    private UsersFragment usersFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_albums:
                    navigateToAlbums();
                    return true;
                case R.id.navigation_photos:
                    navigateToPhotos();
                    return true;
                case R.id.navigation_users:
                    navigateToUsers();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bnv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigateToAlbums();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            photosFragment.filterActionClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToAlbums() {
        if (albumsFragment == null) {
            albumsFragment = new AlbumsFragment();
        }
        changeFragment(albumsFragment);
    }

    private void navigateToPhotos() {
        if (photosFragment == null) {
            photosFragment = new PhotosFragment();
        }
        changeFragment(photosFragment);
    }

    private void navigateToUsers() {
        if (usersFragment == null) {
            usersFragment = new UsersFragment();
        }
        changeFragment(usersFragment);
    }

    private void changeFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, newFragment)
                .commit();
    }
}
