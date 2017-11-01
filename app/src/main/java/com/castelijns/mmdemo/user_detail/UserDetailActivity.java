package com.castelijns.mmdemo.user_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
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

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_website)
    TextView tvWebsite;

    @BindView(R.id.tv_city)
    TextView tvCity;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_zipcode)
    TextView tvZipcode;

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    @BindView(R.id.tv_company_catchphrase)
    TextView tvCompanyCatchphrase;

    @BindView(R.id.tv_company_bs)
    TextView tvCompanyBusinessStatement;

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

        tvPhone.setText(user.getPhone());
        tvWebsite.setText(user.getWebsite());

        tvCity.setText(user.getAddress().getCity());
        tvAddress.setText(String.format("%s, %s", user.getAddress().getStreet(),
                user.getAddress().getSuite()));
        tvZipcode.setText(user.getAddress().getZipcode());

        tvCompanyName.setText(user.getCompany().getName());
        tvCompanyCatchphrase.setText(user.getCompany().getCatchPhrase());
        tvCompanyBusinessStatement.setText(user.getCompany().getBs());

        presenter = new UserDetailPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
