package com.castelijns.mmdemo.users;

import com.castelijns.mmdemo.models.User;
import com.castelijns.mmdemo.network.ApiManager;
import com.castelijns.mmdemo.network.ApiService;

import java.util.List;

import io.reactivex.Observable;

class UsersRepo {

    private static UsersRepo instance = null;

    private static ApiService apiService;

    private UsersRepo() {
        apiService = ApiManager.getService();
    }

    static UsersRepo getInstance() {
        if (instance == null) {
            instance = new UsersRepo();
        }

        return instance;
    }

    Observable<List<User>> getAllUsers() {
        return apiService.getAllUsers();
    }
}
