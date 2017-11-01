package com.castelijns.mmdemo.photos;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.models.Photo;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class PhotoSection extends StatelessSection {

    private ItemClickListener itemClickListener;

    private Context context;
    private int albumId;
    private List<Photo> photos;
    private int itemWidth;

    PhotoSection(Context context, int albumId, List<Photo> photos, int itemWidth) {
        super(new SectionParameters.Builder(R.layout.photos_section_item)
                .headerResourceId(R.layout.photos_section_header)
                .build());

        this.context = context;
        this.albumId = albumId;
        this.photos = photos;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getContentItemsTotal() {
        return photos.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Photo photo = photos.get(position);
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        String thumbnailurl = photo.getThumbnailUrl();

        itemHolder.ivPhoto.getLayoutParams().width = itemWidth;
        itemHolder.ivPhoto.getLayoutParams().height= itemWidth;
        itemHolder.ivPhoto.requestLayout();

        Glide.with(context)
                .load(thumbnailurl)
                .into(itemHolder.ivPhoto);

        itemHolder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(photo, itemHolder.ivPhoto);
            }
        });

        // For shared element transitions.
        ViewCompat.setTransitionName(itemHolder.ivPhoto, String.format("%s.%s",
                "title", photo.getTitle()));
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvTitle.setText(String.format(Locale.getDefault(),
                "%s %d", context.getString(R.string.album), albumId));
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    interface ItemClickListener {
        void onClick(Photo photo, ImageView ivPhoto);
    }
}
