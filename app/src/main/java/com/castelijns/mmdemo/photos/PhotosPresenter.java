package com.castelijns.mmdemo.photos;

import android.util.SparseArray;
import android.widget.ImageView;

import com.castelijns.mmdemo.albums.AlbumsRepo;
import com.castelijns.mmdemo.models.Album;
import com.castelijns.mmdemo.models.Photo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotosPresenter implements PhotosContract.Presenter {

    private PhotosContract.View view;
    private PhotosRepo photosRepo;
    private AlbumsRepo albumsRepo;

    private Disposable disposable;

    PhotosPresenter(PhotosContract.View view) {
        this.view = view;
        photosRepo = PhotosRepo.getInstance();
        albumsRepo = AlbumsRepo.getInstance();
    }

    @Override
    public void start() {
        view.clearList();
        view.showLoading();

        Observable.zip(photosRepo.getAllPhotos(), albumsRepo.getAllAlbums(),
                (photos, albums) -> {
                    Pair pair = new Pair();
                    pair.photos = photos;
                    pair.albums = albums;
                    return pair;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(pair -> {
                    // Assign album titles to photos;
                    for (Photo photo : pair.photos) {
                        for (Album album : pair.albums) {
                            if (album.getId() == photo.getAlbumId()) {
                                photo.setAlbumTitle(album.getTitle());
                            }
                        }
                    }

                    return pair;
                })
                .doOnNext(pair -> {
                    photosRepo.cacheData(pair.photos);
                    albumsRepo.cacheData(pair.albums);
                })
                .map(pair -> {
                    // Split albums by albumId.
                    SparseArray<List<Photo>> albumPhotos = new SparseArray<>();
                    for (Photo photo : pair.photos) {
                        List<Photo> photoList = albumPhotos.get(photo.getAlbumId(), new ArrayList<>());
                        photoList.add(photo);

                        albumPhotos.append(photo.getAlbumId(), photoList);
                    }
                    return albumPhotos;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SparseArray<List<Photo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(SparseArray<List<Photo>> albumPhotos) {
                        view.showPhotos(albumPhotos);

                        int photoCount = 0;
                        for (int i = 0; i < albumPhotos.size(); i++) {
                            photoCount += albumPhotos.valueAt(i).size();
                        }
                        view.showPhotoCount(photoCount, albumPhotos.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void start(int albumId) {
        filterAlbums(albumId);
    }

    @Override
    public void stop() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onPhotoClicked(Photo photo, ImageView ivPhoto) {
        view.showPhotoDetail(photo, ivPhoto);
    }

    @Override
    public void filterAlbums(int albumId) {
        view.clearList();
        view.showLoading();

        Observable.zip(photosRepo.getAllPhotosForAlbumId(albumId), albumsRepo.getAlbumById(albumId),
                (photos, album) -> {
                    Pair pair = new Pair();
                    pair.photos = photos;
                    pair.album = album;
                    return pair;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(pair -> {
                    // Assign album title to photos;
                    for (Photo photo : pair.photos) {
                        photo.setAlbumTitle(pair.album.getTitle());
                    }

                    return pair;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Pair pair) {
                        if (pair.photos.size() > 0) {
                            SparseArray<List<Photo>> albumPhotos = new SparseArray<>();
                            albumPhotos.append(pair.photos.get(0).getAlbumId(), pair.photos);

                            view.showPhotos(albumPhotos);
                            view.showPhotoCount(pair.photos.size(), 1);
                        } else {
                            view.showPhotos(new SparseArray<>());
                            view.showPhotoCount(0, 0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });

    }

    private class Pair {
        List<Photo> photos;
        List<Album> albums;

        Album album; // Only in case of filter.
    }
}
