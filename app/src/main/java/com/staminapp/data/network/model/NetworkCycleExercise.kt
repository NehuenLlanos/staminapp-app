package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName
import com.staminapp.data.model.Cycle
import com.staminapp.data.model.Exercise

data class NetworkCycleExercise(
    @SerializedName("order")
    var order: Int? = null,

    @SerializedName("duration")
    var duration: Int? = null,

    @SerializedName("repetitions")
    var repetitions: Int? = null,

    @SerializedName("exercise")
    var exercise: NetworkExercise? = null
) {
    fun asModel(): Exercise {
        return Exercise(
            id = exercise?.id?: 0,
            name = exercise?.name?: "",
            detail = exercise?.detail?: "",
            duration = duration?: 0,
            repetitions = repetitions?: 0,
            order = order?: 1,
        )
    }
}
