package com.example.noteapp

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

//@Parcelize
//@JsonClass(generateAdapter = true)
data class Note(
    var title: String,
    var content: String,
    val creation: String
)