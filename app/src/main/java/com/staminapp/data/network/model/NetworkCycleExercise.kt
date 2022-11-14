package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkCycleExercise(
    @SerializedName("order")
    var order: Int? = null,

    @SerializedName("duration")
    var duration: Int? = null,

    @SerializedName("repetitons")
    var repetitions: Int? = null,

    @SerializedName("exercise")
    var exercise: NetworkExercise? = null
)
