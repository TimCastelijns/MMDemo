package com.castelijns.mmdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
        val id: Int,
        val albumId: Int,
        var albumTitle: String,
        val title: String,
        val url: String,
        val thumbnailUrl: String
) : Comparable<Photo>, Parcelable {

    override fun compareTo(other: Photo) = title.compareTo(other.title)

}
