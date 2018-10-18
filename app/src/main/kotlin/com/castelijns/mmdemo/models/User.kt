package com.castelijns.mmdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val address: Address,
        val phone: String,
        val website: String,
        val company: Company
) : Comparable<User>, Parcelable {

    override fun compareTo(other: User) = name.compareTo(other.name)
}
