package com.castelijns.mmdemo.albums;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.models.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Album> dataSet;
    private ItemClickListener itemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_username)
        TextView tvUserName;

        @BindView(R.id.btn_show_photos)
        Button btnShowPhotos;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    AlbumsAdapter(Context context, List<Album> dataSet) {
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Album album = dataSet.get(position);

        holder.tvTitle.setText(album.getTitle());
        holder.tvUserName.setText(album.getUserName());
        holder.btnShowPhotos.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onShowPhotosClicked(dataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onShowPhotosClicked(Album album);
    }
}
