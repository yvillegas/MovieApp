package com.yvillegas.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Cast (
    val id: Long? = null,
    val name: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    val character: String? = null,
)