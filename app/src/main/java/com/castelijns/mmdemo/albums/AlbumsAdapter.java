package com.castelijns.mmdemo.albums;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.models.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Album> dataSet;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

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
        holder.tvTitle.setText(dataSet.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
