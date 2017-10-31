package com.castelijns.mmdemo.user_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseActivity;
import com.castelijns.mmdemo.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_EMAIL_TRANSITION;
import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_USER;
import static com.castelijns.mmdemo.users.UsersFragment.EXTRA_USERNAME_TRANSITION;

public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

    @BindView(R.id.tv_username)
    TextView tvUsername;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    private UserDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        User user = getIntent().getParcelableExtra(EXTRA_USER);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(user.getName());

        // For shared element transitions.
        supportPostponeEnterTransition();

        tvUsername.setTransitionName(getIntent().getStringExtra(EXTRA_USERNAME_TRANSITION));
        tvUsername.setText(user.getUsername());

        tvEmail.setTransitionName(getIntent().getStringExtra(EXTRA_EMAIL_TRANSITION));
        tvEmail.setText(user.getEmail());

        supportStartPostponedEnterTransition();
        // end.

        presenter = new UserDetailPresenter(this);
    }
}
