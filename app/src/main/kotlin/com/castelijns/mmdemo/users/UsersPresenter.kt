package com.castelijns.mmdemo.users

import android.widget.TextView
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.models.User

class UsersPresenter internal constructor(private val view: UsersContract.View) : BasePresenter(){
    private val repo=  UsersRepo


    override fun start() {
//        disposable = repo.getAllUsers()
//                .subscribeOn(Schedulers.io())
//                .doOnNext { users -> repo.cacheData(users) }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { users ->
//                    Collections.sort(users)
//                    view.showUsers(users)
//                    view.showUserCount(users.size)
//                }

    }

    fun onUserClicked(user: User, tvUsername: TextView, tvEmail: TextView) {
        this.view.showUserDetail(user, tvUsername, tvEmail)
    }
}
