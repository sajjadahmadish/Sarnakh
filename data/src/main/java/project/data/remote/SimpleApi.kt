package project.data.remote


import io.reactivex.Flowable
import project.data.model.*

/**
 * Very simplified [Api] functions for use in [project.data.AppDataManager]
 *
 *
 * For each function, enter any required parameters
 */
interface SimpleApi {


    fun session(): Flowable<SessionResponse>

    fun register(firebaseToken: String): Flowable<RegisterResponse>

    fun login(
        username: String,
        pass: String,
        phoneIp: String,
        phoneLocation: String,
        phoneName: String,
        osName: String,
        osVersionCode: String,
        osVersionName: String,
        deviceId: String
    ): Flowable<LoginResponse>

    fun userInfo() : Flowable<UserResponse>

    fun logout() : Flowable<BaseResponse>

    fun markers() : Flowable<MarkerResponse>

}
