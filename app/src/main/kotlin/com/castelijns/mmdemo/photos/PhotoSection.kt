package com.castelijns.mmdemo.photos

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castelijns.mmdemo.R
import com.castelijns.mmdemo.models.Photo
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.photos_section_header.view.*
import kotlinx.android.synthetic.main.photos_section_item.view.*
import java.util.*

class PhotoSection(
        private val context: Context,
        private val albumId: Int,
        private val albumName: String,
        private val photos: List<Photo>,
        private val itemWidth: Int
) : StatelessSection(SectionParameters.Builder(R.layout.photos_section_item)
        .headerResourceId(R.layout.photos_section_header)
        .build()) {

    private var itemClickListener: ItemClickListener? = null

    override fun getContentItemsTotal(): Int {
        return photos.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = photos[position]

        val thumbnailUrl = photo.thumbnailUrl

        holder.itemView.iv_photo.apply {
            layoutParams.width = itemWidth
            layoutParams.height = itemWidth
            requestLayout()
        }

        Glide.with(context)
                .load(thumbnailUrl)
                .into(holder.itemView.iv_photo)

        holder.itemView.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onClick(photo, holder.itemView.iv_photo)
            }
        }

        // For shared element transitions.
        ViewCompat.setTransitionName(holder.itemView.iv_photo, String.format("%s.%s",
                "title", photo.title))
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        val headerHolder = holder as HeaderViewHolder

        headerHolder.itemView.tv_id.text = String.format(Locale.getDefault(),
                "%s %d", context.getString(R.string.album), albumId)
        headerHolder.itemView.tv_title.text = albumName
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    interface ItemClickListener {
        fun onClick(photo: Photo, ivPhoto: ImageView)
    }
}
