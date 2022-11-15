package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class NetworkExercise(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("detail")
    var detail: String? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("duration")
    var duration: Int? = null,

    @SerializedName("date")
    var date: Date? = null,

    @SerializedName("metadata")
    var metadata: String? = null
)
