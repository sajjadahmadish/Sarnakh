package project.utils.network

import com.novoda.merlin.MerlinsBeard
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit


fun MerlinsBeard.hasInternetAccess(timeOut: Int, callback: (hasAccess: Boolean) -> Unit) {
    var response = false
    val subscribe = Observable
        .timer(timeOut.toLong(), TimeUnit.SECONDS)
        .subscribe {
            response = true
            callback.invoke(false)
        }
    hasInternetAccess {
        subscribe.dispose()
        if (!response) {
            callback.invoke(it)
        }
    }
}