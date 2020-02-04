package project.data.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("message")
    val message: String, // login Successfully
    @SerializedName("response")
    val response: Int, // 1
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("gender")
    val gender: String
)