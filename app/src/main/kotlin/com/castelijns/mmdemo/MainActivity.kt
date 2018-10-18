package com.castelijns.mmdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.castelijns.mmdemo.albums.AlbumsFragment
import com.castelijns.mmdemo.app.BaseActivity
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.photos.PhotosFragment
import com.castelijns.mmdemo.users.UsersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.Fragment
import com.castelijns.mmdemo.albums.ItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ItemClickListener {

    private var albumsFragment: AlbumsFragment? = null
    private var photosFragment: PhotosFragment? = null
    private var usersFragment: UsersFragment? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_albums -> {
                navigateToAlbums()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_photos -> {
                navigateToPhotos()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_users -> {
                navigateToUsers()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateToAlbums()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_photos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            photosFragment!!.filterActionClicked()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToAlbums() {
        if (albumsFragment == null) {
            albumsFragment = AlbumsFragment()
            albumsFragment!!.setShowPhotosClickedListener(this)
        }
        changeFragment(albumsFragment!!)
    }

    private fun navigateToPhotos() {
        if (photosFragment == null) {
            photosFragment = PhotosFragment()
        }
        photosFragment!!.arguments = null
        changeFragment(photosFragment!!)
    }

    private fun navigateToPhotosWithAlbumFilter(albumId: Int) {
        if (photosFragment == null) {
            photosFragment = PhotosFragment()
        }

        val args = Bundle()
        args.putInt(EXTRA_ALBUM_ID, albumId)
        photosFragment!!.arguments = args

        changeFragment(photosFragment!!)

        // Update the bottom nav selection manually. It does not update it automatically
        // if fragments are replaced without being triggered by a tap on the item.
        bottomNavigationView.menu.findItem(R.id.navigation_photos).isChecked = true
    }

    private fun navigateToUsers() {
        if (usersFragment == null) {
            usersFragment = UsersFragment()
        }
        changeFragment(usersFragment!!)
    }

    private fun changeFragment(newFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, newFragment)
                .commit()
    }

    override fun onShowPhotosClicked(album: Album) {
        navigateToPhotosWithAlbumFilter(album.id)
    }

    companion object {
        const val EXTRA_ALBUM_ID = "album_id"
    }
}
