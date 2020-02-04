package project.utils.okHttp

import com.blankj.utilcode.util.StringUtils
import okhttp3.Interceptor
import okhttp3.Response
import project.Session

class TokenRenewInterceptor(private val session: Session) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 401) {
            session.invalidate(true)
        } else if (response.code == 200) {

            val newToken: String? = response.header("token")
            if (newToken != null) {
                session.token = newToken
                if (!StringUtils.isTrimEmpty(newToken) && !session.isLoggedIn)
                    session.isLoggedIn = true
            }
        }

        return response
    }
}