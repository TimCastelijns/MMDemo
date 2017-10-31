package com.castelijns.mmdemo.app;

import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment implements BaseView {

    @Override
    public Context getContext() {
        return getActivity();
    }
}
