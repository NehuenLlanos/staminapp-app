package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkReview(
    @SerializedName("score")
    var score: Int? = null,

    @SerializedName("review")
    var review: String? = null
)
