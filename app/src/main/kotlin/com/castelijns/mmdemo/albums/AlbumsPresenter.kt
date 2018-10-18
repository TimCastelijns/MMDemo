package com.castelijns.mmdemo.albums

import com.castelijns.mmdemo.models.Album
import com.castelijns.mmdemo.models.User
import com.castelijns.mmdemo.users.UsersRepo

import java.util.Collections

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class AlbumsPresenter internal constructor(private val view: AlbumsContract.View) : AlbumsContract.Presenter {
    private val albumsRepo = AlbumsRepo
    private val usersRepo = UsersRepo

    private var disposable: Disposable? = null

    override fun start() {
        view.showLoading()
        Observable.zip<List<Album>, List<User>, Pair>(albumsRepo.getAllAlbums(), usersRepo.getAllUsers(), BiFunction{ albums, users ->
            val pair = Pair()
            pair.albums = albums
            pair.users = users
            pair
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { pair ->
                    Collections.sort(pair.albums!!)

                    // Assign user names to albums.
                    for (album in pair.albums!!) {
                        for ((id, name) in pair.users!!) {
                            if (id == album.userId) {
                                album.userName = name
                            }
                        }
                    }

                    pair
                }
                .doOnNext { pair ->
                    albumsRepo.cacheData(pair.albums!!)
                    usersRepo.cacheData(pair.users!!)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Pair> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(pair: Pair) {
                        view.showAlbums(pair.albums!!)
                        view.showAlbumCount(pair.albums!!.size)
                    }

                    override fun onError(e: Throwable) {
                        view.hideLoading()
                        view.showError()
                    }

                    override fun onComplete() {
                        view.hideLoading()
                    }
                })
    }

    override fun stop() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    private inner class Pair {
        internal var albums: List<Album>? = null
        internal var users: List<User>? = null
    }
}
