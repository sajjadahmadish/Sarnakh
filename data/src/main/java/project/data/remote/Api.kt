package project.data.remote

import io.reactivex.Flowable
import io.reactivex.Observable
import project.data.model.*
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * all of webservice functions without any cha nges
 */
interface Api {

//    @FormUrlEncoded
//    @POST(ApiEndPoint.REGISTER)
//    fun registerUser(@Header("api_key") apiKey: String, @FieldMap map: Map<String, String>): Flowable<RegisterResponse>

    @POST(ApiEndPoint.LOGOUT)
    fun logout(): Flowable<BaseResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    fun login(@FieldMap map: Map<String, String>): Flowable<LoginResponse>

    @POST(ApiEndPoint.SESSION)
    fun getSession(): Flowable<SessionResponse>

    @POST(ApiEndPoint.USER_INFO)
    fun getUserInfo(): Flowable<UserResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.REGISTER)
    fun register(@FieldMap map: Map<String, String>): Flowable<RegisterResponse>

    @POST(ApiEndPoint.MARKER)
    fun getMarkers(): Flowable<MarkerResponse>



}