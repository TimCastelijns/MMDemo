package com.castelijns.mmdemo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo
) : Parcelable

@Parcelize
data class Geo(
        val lat: String,
        val lng: String
) : Parcelable
