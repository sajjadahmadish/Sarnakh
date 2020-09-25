package project.utils.okHttp

import okhttp3.Interceptor
import okhttp3.Response
import project.Session
import java.io.IOException

class AuthorizationInterceptor(private val apiKey: String, private val session: Session?) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val builder = request.newBuilder()
        builder.addHeader("apiKey", apiKey)

        if (session!!.isLoggedIn) {
            builder.addHeader("username", session.username)
            builder.addHeader("token", session.token)
        }

        val request1 = builder.build()

        return chain.proceed(request1)
    }

}
