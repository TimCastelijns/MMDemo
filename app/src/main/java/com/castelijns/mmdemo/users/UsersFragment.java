package com.castelijns.mmdemo.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseFragment;
import com.castelijns.mmdemo.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsersFragment extends BaseFragment implements UsersContract.View {

    @BindView(R.id.rv_users)
    RecyclerView rvUsers;

    private UsersPresenter presenter;

    private UsersAdapter adapter;
    private List<User> users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new UsersPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users = new ArrayList<>();
        adapter = new UsersAdapter(getContext(), users);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setHasFixedSize(true);
        rvUsers.setAdapter(adapter);

        presenter.start();
    }

    @Override
    public void showUsers(List<User> users) {
        this.users.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
