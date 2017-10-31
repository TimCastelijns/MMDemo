package com.castelijns.mmdemo.users;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<User> dataSet;
    private ItemClickListener itemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_username)
        TextView tvUsername;

        @BindView(R.id.tv_email)
        TextView tvEmail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    UsersAdapter(Context context, List<User> dataSet) {
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_user, parent, false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, final int position) {
        final User user = dataSet.get(position);
        holder.tvName.setText(user.getName());
        holder.tvUsername.setText(user.getUsername());
        holder.tvEmail.setText(user.getEmail());

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(user, holder.tvUsername, holder.tvEmail);
            }
        });

        // For shared element transitions.
        ViewCompat.setTransitionName(holder.tvUsername, String.format(
                "%s.%s","username", user.getUsername()));
        ViewCompat.setTransitionName(holder.tvEmail, String.format(
                "%s.%s", "email", user.getName()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    interface ItemClickListener {
        void onClick(User user, TextView tvUsername, TextView tvEmail);
    }
}