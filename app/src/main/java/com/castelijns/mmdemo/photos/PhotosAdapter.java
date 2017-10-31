package com.castelijns.mmdemo.photos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.models.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Photo> dataSet;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    PhotosAdapter(Context context, List<Photo> dataSet) {
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_photo, parent, false);
        return new PhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(dataSet.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
