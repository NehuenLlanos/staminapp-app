package com.staminapp.data.model

import java.util.*

data class User (
    var id: Int?,
    var username: String,
    var firstName: String,
    var email: String,
    var gender: String,
    var birthdate: Date? = null,
    var image: String? = null,
)