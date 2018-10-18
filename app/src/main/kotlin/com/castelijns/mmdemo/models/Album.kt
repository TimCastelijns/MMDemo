package com.castelijns.mmdemo.models

data class Album(
        val id: Int,
        val userId: Int,
        var userName: String,
        val title: String
) : Comparable<Album> {

    override fun compareTo(other: Album) = title.compareTo(other.title)

}
