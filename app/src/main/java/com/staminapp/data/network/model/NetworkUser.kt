package com.staminapp.data.network.model

import com.google.gson.annotations.SerializedName
import com.staminapp.data.model.User
import java.util.*

data class NetworkUser (

    @SerializedName("id"           ) var id           : Int?     = null,
    @SerializedName("username"     ) var username     : String,
    @SerializedName("firstName"    ) var firstName    : String,
    @SerializedName("lastName"     ) var lastName     : String,
    @SerializedName("gender"       ) var gender       : String?  = null,
    @SerializedName("birthdate"    ) var birthdate    : Date,
    @SerializedName("email"        ) var email        : String,
    @SerializedName("phone"        ) var phone        : String?  = null,
    @SerializedName("avatarUrl"    ) var avatarUrl    : String?  = null,
    @SerializedName("metadata"     ) var metadata     : NetworkUserMetadata?  = null,
    @SerializedName("date"         ) var date         : Date,
    @SerializedName("lastActivity" ) var lastActivity : Date?    = null,
    @SerializedName("verified"     ) var verified     : Boolean? = null

) {
    fun asModel() : User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            lastActivity = lastActivity
        )
    }
}