package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

class NetworkError (
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<String>? = null
)