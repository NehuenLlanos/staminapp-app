package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName
import com.staminapp.data.model.Routine
import com.staminapp.data.model.User
import java.util.*

data class NetworkRoutine(
    @SerializedName("id")
    var id : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("detail")
    var detail : String,

    @SerializedName("date")
    var date : Long,

    @SerializedName("score")
    var score : Int,

    @SerializedName("isPublic")
    var isPublic : Boolean,

    @SerializedName("difficulty")
    var difficulty : String,

    @SerializedName("user")
    var user : NetworkUser? = null,

    @SerializedName("category")
    var category : NetworkCategory? = null,

    @SerializedName("metadata")
    var metadata : NetworkRoutineMetadata? = null
) {
    fun asModel(): Routine {
        return Routine(
            id = id?: 0,
            name = name?: "",
            detail = detail?: "",
            score = score?: 0,
            difficulty = difficulty?: "rookie",
            date = date,
            user = user?.asModel(),
            image = metadata?.picture,
        )
    }
}
