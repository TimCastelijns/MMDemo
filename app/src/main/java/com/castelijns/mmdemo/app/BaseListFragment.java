package com.castelijns.mmdemo.app;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.castelijns.mmdemo.R;

import butterknife.BindView;

public abstract class BaseListFragment extends BaseFragment implements BaseListView {

    @BindView(R.id.tv_header)
    TextView tvHeader;

    @BindView(R.id.pb)
    ProgressBar pb;

    protected void setHeaderText(String text) {
        tvHeader.setText(text);
    }

    @Override
    public void showLoading() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.error_retrieving_data, Toast.LENGTH_SHORT)
                .show();
    }
}
