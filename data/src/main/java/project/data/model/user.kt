package project.data.model

data class UserResponse(
    val message: String,
    val response: Int,
    val userInfo: UserInfo?
)

data class UserInfo (
    val email: String,
    val first_name: String,
    val gender: String,
    val last_name: String,
    val type: String,
    val username: String
)