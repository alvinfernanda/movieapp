package com.alvinfernanda.mobile.movieapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "page") var page: Int?,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false,
) : Parcelable