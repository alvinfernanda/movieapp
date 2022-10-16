package com.alvinfernanda.mobile.movieapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val page: Int?,
    val poster_path: String?,
    val title: String?,
    val overview: String?,
    val release_date: String?,
    var favorite: Boolean = false,
) : Parcelable