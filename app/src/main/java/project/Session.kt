package project

interface Session {

    var isLoggedIn: Boolean

    var token: String

    var username: String

    fun invalidate(fromApi: Boolean)

}