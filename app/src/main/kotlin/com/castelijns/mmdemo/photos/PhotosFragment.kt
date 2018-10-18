package com.castelijns.mmdemo.photos

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.castelijns.mmdemo.MainActivity
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.app.BaseListFragment
import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.photodetail.PhotoDetailActivity
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_photos.*
import java.util.*

class PhotosFragment : BaseListFragment(), PhotosContract.View {

    private var presenter: PhotosPresenter? = null

    private var sectionAdapter: SectionedRecyclerViewAdapter? = null
    private var itemWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // Calculate how wide one col is allowed to be.
        val displaymetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displaymetrics)
        itemWidth = displaymetrics.widthPixels / PHOTOS_GRID_COLS

        presenter = PhotosPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectionAdapter = SectionedRecyclerViewAdapter()

        val gridLayoutManager = GridLayoutManager(getContext(), PHOTOS_GRID_COLS)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (sectionAdapter!!.getSectionItemViewType(position)) {
                    SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER -> PHOTOS_GRID_COLS
                    else -> 1
                }
            }
        }

        rv_photos.layoutManager = gridLayoutManager

        // If args were passed, presenter should start by doing something different from default.
        val args = arguments
        if (args != null) {
            val albumId = arguments!!.getInt(MainActivity.EXTRA_ALBUM_ID)
            presenter!!.start(albumId)
        } else {
            presenter!!.start()
        }
    }

    override fun onDestroyView() {
        presenter!!.stop()
        super.onDestroyView()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu!!.findItem(R.id.action_filter).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    fun filterActionClicked() {
        val view = layoutInflater.inflate(R.layout.dialog_input, null)
        AlertDialog.Builder(getContext()!!)
                .setTitle(R.string.filter_albums_title)
                .setMessage(R.string.filter_albums_message)
                .setView(view)
                .setPositiveButton(R.string.filter) { dialog, which ->
                    val albumId = Integer.parseInt((view.findViewById<View>(R.id.et_input) as EditText)
                            .text.toString())
                    presenter!!.filterAlbums(albumId)
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
    }

    override fun clearList() {
        sectionAdapter!!.removeAllSections()
    }

    override fun showPhotosByAlbum(photosByAlbum: Map<Album, List<Photo>>) {
        photosByAlbum.keys.forEach { album ->
            val section = PhotoSection(activity!!, album.id, album.title, photosByAlbum[album]!!,
                    itemWidth)

            section.setItemClickListener(object : PhotoSection.ItemClickListener {
                override fun onClick(photo: Photo, ivPhoto: ImageView) {
                    presenter!!.onPhotoClicked(photo, ivPhoto)
                }
            })

            sectionAdapter?.addSection(section)
        }

        rv_photos.adapter = sectionAdapter
    }

    override fun showPhotoCount(photoCount: Int, albumCount: Int) {
        setHeaderText(String.format(Locale.getDefault(),
                "%d %s in %d %s", photoCount, getString(R.string.header_photos), albumCount,
                getString(R.string.header_albums)))
    }

    override fun showPhotoDetail(photo: Photo, ivPhoto: ImageView) {
        val transitionName = ViewCompat.getTransitionName(ivPhoto)

        val intent = Intent(getContext(), PhotoDetailActivity::class.java)
        intent.putExtra(EXTRA_PHOTO, photo)
        intent.putExtra(EXTRA_PHOTO_TRANSITION, transitionName)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!, ivPhoto, transitionName!!)

        startActivity(intent, options.toBundle())
    }

    companion object {

        const val PHOTOS_GRID_COLS = 5

        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_PHOTO_TRANSITION = "extra_photo_transition"
    }
}
