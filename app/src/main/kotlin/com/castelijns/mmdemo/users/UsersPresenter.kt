package com.castelijns.mmdemo.users

import android.widget.TextView

import com.castelijns.mmdemo.models.User

import java.util.Collections

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UsersPresenter internal constructor(private val view: UsersContract.View) : UsersContract.Presenter {
    private val repo=  UsersRepo

    private var disposable: Disposable? = null

    override fun start() {
        disposable = repo.getAllUsers()
                .subscribeOn(Schedulers.io())
                .doOnNext { users -> repo.cacheData(users) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { users ->
                    Collections.sort(users)
                    view.showUsers(users)
                    view.showUserCount(users.size)
                }

    }

    override fun stop() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onUserClicked(user: User, tvUsername: TextView, tvEmail: TextView) {
        this.view.showUserDetail(user, tvUsername, tvEmail)
    }
}
