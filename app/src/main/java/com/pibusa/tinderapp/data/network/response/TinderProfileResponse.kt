package com.pibusa.tinderapp.data.network.response

data class TinderProfileResponse(
    var id: Int? = null,
    var gender: String,
    val user: Name,
    val location: Location,
    var email: String,
    var picture: String,
    var username: String,
    var registered: Long,
    var dob: Long,
    var phone: String,
    var cell: String,
    var SSN: String
)
