package com.castelijns.mmdemo.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseListFragment;
import com.castelijns.mmdemo.models.User;
import com.castelijns.mmdemo.user_detail.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class UsersFragment extends BaseListFragment implements UsersContract.View {

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_USERNAME_TRANSITION = "extra_username_transition";
    public static final String EXTRA_EMAIL_TRANSITION = "extra_email_transition";

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
        adapter.setItemClickListener((user, tvUsername, tvEmail) -> presenter.onUserClicked(
                user, tvUsername, tvEmail));

        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setHasFixedSize(true);
        rvUsers.setAdapter(adapter);

        presenter.start();
    }

    @Override
    public void onDestroyView() {
        presenter.stop();
        super.onDestroyView();
    }

    @Override
    public void showUsers(List<User> users) {
        this.users.addAll(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showUserCount(int count) {
        setHeaderText(String.format(Locale.getDefault(), "%d %s", count, getString(R.string.header_users)));
    }

    @Override
    public void showUserDetail(User user, TextView tvUsername, TextView tvEmail) {
        String transitionUsername = ViewCompat.getTransitionName(tvUsername);
        String transitionEmail = ViewCompat.getTransitionName(tvEmail);

        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra(EXTRA_USER, user);
        intent.putExtra(EXTRA_USERNAME_TRANSITION, transitionUsername);
        intent.putExtra(EXTRA_EMAIL_TRANSITION, transitionEmail);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                Pair.create(tvUsername, transitionUsername),
                Pair.create(tvEmail, transitionEmail)
        );

        startActivity(intent, options.toBundle());
    }
}
