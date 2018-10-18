package com.castelijns.mmdemo.user_detail

import com.castelijns.mmdemo.models.User

class UserDetailPresenter internal constructor(private val view: UserDetailContract.View, private val user: User) : UserDetailContract.Presenter {

    override fun start() {

    }

    override fun stop() {

    }

    override fun onDirectionsClicked() {
        view.showNavigationTo(user.address.geo["lat"]!!, user.address.geo["lon"]!!)
    }
}
