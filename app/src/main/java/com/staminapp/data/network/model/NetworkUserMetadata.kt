package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkUserMetadata(
    @SerializedName("profilePicture")
    var profilePicture: String? = null,

    @SerializedName("weight")
    var weight: List<Float>? = null,

    @SerializedName("height")
    var height: List<Float>? = null,

    @SerializedName("firstLogIn")
    var firstLogIn: Boolean? = null,
)