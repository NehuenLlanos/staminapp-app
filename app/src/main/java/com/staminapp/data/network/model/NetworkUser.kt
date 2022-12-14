package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName
import com.staminapp.data.model.User
import java.util.*

data class NetworkUser(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("firstName")
    var firstName: String? = null,

    @SerializedName("lastName")
    var lastName: String? = null,

    @SerializedName("gender")
    var gender: String? = null,

    @SerializedName("birthdate")
    var birthdate: Date? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("phone")
    var phone: String? = null,

    @SerializedName("avatarUrl")
    var avatarUrl: String? = null,

    @SerializedName("metadata")
    var metadata: NetworkUserMetadata? = null,

    @SerializedName("date")
    var date: Date? = null,

    @SerializedName("lastActivity")
    var lastActivity: Date? = null,

    @SerializedName("verified")
    var verified: Boolean? = null

) {
    fun asModel(): User {
        return User(
            id = id,
            username = username?: "",
            firstName = firstName?: "",
            email = email?: "",
            gender = gender?: "other",
            birthdate = birthdate,
            image = metadata?.profilePicture,
        )
    }
}