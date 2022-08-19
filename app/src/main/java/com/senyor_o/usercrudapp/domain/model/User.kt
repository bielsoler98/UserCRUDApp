package com.senyor_o.usercrudapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String,
    @Json(name = "birthdate")
    val birthdate: String
)
