package com.castelijns.mmdemo.userdetail

import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.models.User

class UserDetailPresenter (
        private val view: UserDetailContract.View,
        private val user: User
) : BasePresenter() {

    override fun start() {

    }

    fun onDirectionsClicked() {
        view.showNavigationTo(user.address.geo.lat, user.address.geo.lng)
    }
}
