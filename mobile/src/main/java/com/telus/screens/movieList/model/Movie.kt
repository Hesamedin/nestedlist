package com.telus.screens.movieList.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val id: String,
    val title: String,
    val description: String,
    val rating: String,
    val length: String,
    val year: String,
    val poster: String,
    val category: String
) : Parcelable