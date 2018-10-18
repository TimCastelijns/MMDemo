package com.castelijns.mmdemo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.castelijns.mmdemo.albums.AlbumsAdapter;
import com.castelijns.mmdemo.albums.AlbumsFragment;
import com.castelijns.mmdemo.app.BaseActivity;
import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.photos.PhotosFragment;
import com.castelijns.mmdemo.users.UsersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AlbumsAdapter.ItemClickListener {

    public static final String EXTRA_ALBUM_ID = "album_id";

    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    private AlbumsFragment albumsFragment;
    private PhotosFragment photosFragment;
    private UsersFragment usersFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
            albumsFragment.setShowPhotosClickedListener(this);
        }
        changeFragment(albumsFragment);
    }

    private void navigateToPhotos() {
        if (photosFragment == null) {
            photosFragment = new PhotosFragment();
        }
        photosFragment.setArguments(null);
        changeFragment(photosFragment);
    }

    private void navigateToPhotosWithAlbumFilter(int albumId) {
        if (photosFragment == null) {
            photosFragment = new PhotosFragment();
        }

        Bundle args = new Bundle();
        args.putInt(EXTRA_ALBUM_ID, albumId);
        photosFragment.setArguments(args);

        changeFragment(photosFragment);

        // Update the bottom nav selection manually. It does not update it automatically
        // if fragments are replaced without being triggered by a tap on the item.
        bnv.getMenu().findItem(R.id.navigation_photos).setChecked(true);
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

    @Override
    public void onShowPhotosClicked(Album album) {
        navigateToPhotosWithAlbumFilter(album.getId());
    }
}
