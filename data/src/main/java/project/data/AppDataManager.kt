package project.data

import io.reactivex.Flowable
import io.reactivex.Observable
import project.data.local.db.DbHelper
import project.data.local.preferences.PreferenceHelper
import project.data.model.*
import project.data.remote.ServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject
constructor(
    private val mDbHelper: DbHelper,
    private val mPreferencesHelper: PreferenceHelper,
    private val mApiHelper: ServiceApi
) : DataManager {




    //region Database

    override val markers: Flowable<List<Marker>>
        get() = mDbHelper.markers

    override fun insertMarker(markers: List<Marker>): Observable<Boolean> {
        return mDbHelper.insertMarker(markers)
    }

    override fun deleteListMarker(idList: List<String>): Observable<Boolean> {
        return mDbHelper.deleteListMarker(idList)
    }

    override fun truncateMarker(): Observable<Boolean> {
        return mDbHelper.truncateMarker()
    }


    //endregion


    //region Api

    override fun markers(): Flowable<MarkerResponse> {
        return mApiHelper.markers()
    }


    override fun logout(): Flowable<BaseResponse> {
        return mApiHelper.logout()
    }



    override fun register(firebaseToken: String): Flowable<RegisterResponse> = mApiHelper.register(
        mapOf(
            "firebase_token" to firebaseToken
        )
    )

    override fun userInfo(): Flowable<UserResponse> {
        return mApiHelper.getUserInfo()
    }


    override fun login(
        username: String,
        pass: String,
        phoneIp: String,
        phoneLocation: String,
        phoneName: String,
        osName: String,
        osVersionCode: String,
        osVersionName: String,
        deviceId: String
    ): Flowable<LoginResponse> = mApiHelper.login(
        mapOf(
            "username" to username,
            "pass" to pass,
            "client_ip" to phoneIp,
            "client_location" to phoneLocation,
            "device_name" to phoneName,
            "os_name" to osName,
            "os_version_code" to osVersionCode,
            "os_version_name" to osVersionName,
            "device_id" to deviceId
        )
    )

    override fun session(): Flowable<SessionResponse> = mApiHelper.sessions()

    override var currentUserId: String?
        get() = mPreferencesHelper.currentUserId
        set(value) {
            mPreferencesHelper.currentUserId = value
//            mApiHelper.apiHeader.protectedApiHeader.userId = value
        }

    override var accessToken: String?
        get() = mPreferencesHelper.accessToken
        set(value) {
            mPreferencesHelper.accessToken = value
//            mApiHelper.apiHeader.protectedApiHeader.accessToken = value
        }


    //endregion


    //region Preferences

    override var showIntro: Boolean
        get() = mPreferencesHelper.showIntro
        set(value) {
            mPreferencesHelper.showIntro = value
        }


    override fun updateUserInfo(
        userId: String?,
        token: String,
        loggedInMode: DataManager.LoggedInMode
    ) {
        updateApiHeader(userId, token)
        userLoggedInMode = loggedInMode
    }


    override var local: String
        get() = mPreferencesHelper.local
        set(value) {
            mPreferencesHelper.local = value
        }


    override var userLoggedInMode: DataManager.LoggedInMode
        get() = mPreferencesHelper.userLoggedInMode
        set(value) {
            mPreferencesHelper.userLoggedInMode = value
        }

    override fun updateApiHeader(userId: String?, token: String?) {
        currentUserId = userId
        accessToken = token
    }


    override var isRegister: Boolean?
        get() = mPreferencesHelper.isRegister
        set(value) {
            mPreferencesHelper.isRegister = value
        }


    override var userFirstName: String?
        get() = mPreferencesHelper.userFirstName
        set(value) {
            mPreferencesHelper.userFirstName = value
        }

    override var userLastName: String?
        get() = mPreferencesHelper.userLastName
        set(value) {
            mPreferencesHelper.userLastName = value
        }

    override var gender: Boolean?
        get() = mPreferencesHelper.gender
        set(value) {
            mPreferencesHelper.gender = value
        }


    override fun updateBriefInfo(firstName: String, lastName: String, gender: Boolean) {
        this.gender = gender
        userFirstName = firstName
        userLastName = lastName
    }


    override var notify: Boolean
        get() = mPreferencesHelper.notify
        set(value) {
            mPreferencesHelper.notify = value
        }


    //endregion

    override fun setUserAsLoggedOut() {
        isRegister = false
        accessToken = ""
        currentUserId = ""
        userLoggedInMode = DataManager.LoggedInMode.LOGGED_OUT

    }

}