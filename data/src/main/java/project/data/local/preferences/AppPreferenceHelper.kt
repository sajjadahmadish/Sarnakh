package project.data.local.preferences

import android.content.Context
import com.google.gson.Gson
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import com.marcinmoskala.kotlinpreferences.gson.GsonSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import project.data.DataManager
import javax.inject.Inject


class AppPreferenceHelper @Inject constructor(@ApplicationContext context: Context) : PreferenceHolder(), PreferenceHelper {

    init {
        setContext(context)
        serializer = GsonSerializer(Gson())
    }

    override var showIntro: Boolean by bindToPreferenceField(false)

    override var currentUserId: String? by bindToPreferenceFieldNullable()

    override var isRegister: Boolean? by bindToPreferenceFieldNullable()

    override var local: String by bindToPreferenceField("fa")

    override var accessToken: String? by bindToPreferenceFieldNullable()

    override var userLoggedInMode: DataManager.LoggedInMode by bindToPreferenceField(DataManager.LoggedInMode.LOGGED_OUT)

    override var userFirstName: String? by bindToPreferenceFieldNullable()

    override var userLastName: String? by bindToPreferenceFieldNullable()

    override var gender: Boolean? by bindToPreferenceFieldNullable()


    override var notify: Boolean by bindToPreferenceField(false)

    override var theme: DataManager.Theme by bindToPreferenceField(DataManager.Theme.THEME_UNDEFINED)

}


