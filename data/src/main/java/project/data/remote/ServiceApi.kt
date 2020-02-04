package project.data.remote

import io.reactivex.Flowable
import io.reactivex.Observable
import project.data.model.*


class ServiceApi(private val api: Api) {


    fun register(map: Map<String, String>): Flowable<RegisterResponse> {
        return api.register(map)
    }

    fun getUserInfo(): Flowable<UserResponse> {
        return api.getUserInfo()
    }

    fun login(map: Map<String, String>): Flowable<LoginResponse> {
        return api.login(map)
    }

    fun sessions(): Flowable<SessionResponse> {
        return api.getSession()
    }

    fun logout(): Flowable<BaseResponse> {
        return api.logout()
    }

    fun markers(): Flowable<MarkerResponse> {
        return api.getMarkers()
    }

}
