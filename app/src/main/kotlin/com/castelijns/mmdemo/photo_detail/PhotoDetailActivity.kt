package com.castelijns.mmdemo.photo_detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.app.BaseActivity
import com.castelijns.mmdemo.models.Photo
import com.castelijns.mmdemo.photos.PhotosFragment.Companion.EXTRA_PHOTO
import com.castelijns.mmdemo.photos.PhotosFragment.Companion.EXTRA_PHOTO_TRANSITION
import kotlinx.android.synthetic.main.activity_photo_detail.*

class PhotoDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val photo = intent.getParcelableExtra<Photo>(EXTRA_PHOTO)

        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = photo.title

        window.statusBarColor = Color.BLACK

        // For shared element transitions.
        supportPostponeEnterTransition()

        iv_photo.transitionName = intent.getStringExtra(EXTRA_PHOTO_TRANSITION)
        Glide.with(this)
                .load(photo.url)
                .into(iv_photo)

        supportStartPostponedEnterTransition()
        // end.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
