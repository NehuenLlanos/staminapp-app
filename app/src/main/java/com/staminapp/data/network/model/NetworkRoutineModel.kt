package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineModel(
    @SerializedName("id")
    var id : Int? = null,

    @SerializedName("name")
    var name : String? = null,

    @SerializedName("detail")
    var detail : String? = null,

    @SerializedName("date")
    var date : Int? = null,

    @SerializedName("score")
    var score : Int? = null,

    @SerializedName("isPublic")
    var isPublic : Boolean? = null,

    @SerializedName("difficulty")
    var difficulty : String? = null,

    @SerializedName("user")
    var user : NetworkUser? = null,

    @SerializedName("category")
    var category : NetworkCategory? = null,

    @SerializedName("metadata")
    var metadata : NetworkRoutineMetadata? = null
) {

}
