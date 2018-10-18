package com.castelijns.mmdemo.userdetail

import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.app.BaseView

interface UserDetailContract {

    interface View : BaseView {
        fun showNavigationTo(lat: String, lon: String)
    }

    interface Presenter : BasePresenter {
        fun onDirectionsClicked()
    }
}
