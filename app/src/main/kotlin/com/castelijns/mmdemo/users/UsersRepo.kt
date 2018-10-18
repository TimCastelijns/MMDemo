package com.castelijns.mmdemo.users

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.User
import com.castelijns.mmdemo.network.ApiManager
import io.reactivex.Observable

object UsersRepo : SimpleCache<User>() {

    private var apiService = ApiManager.service

    fun getAllUsers(): Observable<List<User>> =
            if (cache != null) {
                Observable.just(cache!!)
            } else {
                apiService.getAllUsers()
            }
}
