package com.staminapp.data.model

import com.google.gson.annotations.SerializedName
import com.staminapp.data.network.model.NetworkCategory
import com.staminapp.data.network.model.NetworkRoutineMetadata
import com.staminapp.data.network.model.NetworkUser

data class Routine(
    var id : Int,
    var name : String,
    var detail : String,
    var score : Int,
    var difficulty : String,
    var date : Long,
    var user : User? = null,
    var image : String? = null
)
