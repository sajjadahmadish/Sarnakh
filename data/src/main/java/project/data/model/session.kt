package project.data.model


data class SessionResponse(
    val message: String,
    val response: Int,
    val current: Session?,
    val sessions: List<Session>?
)

data class Session(
    val id: Int,
    val create_date: String,
    val last_online: String,
    val os_name: String,
    val os_version_name: String,
    val phone_ip: String,
    val phone_location: String
)