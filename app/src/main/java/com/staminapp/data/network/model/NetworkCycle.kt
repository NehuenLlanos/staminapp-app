package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkCycle(
    @SerializedName("id")
    var id : Int? = null,

    @SerializedName("name")
    var name : String? = null,

    @SerializedName("detail")
    var detail : String? = null,

    @SerializedName("type")
    var type : String? = null,

    @SerializedName("order")
    var order : Int? = 0,

    @SerializedName("repetitions")
    var repetitions : Int? = 0,

    @SerializedName("metadata")
    var metadata : String? = null
)
