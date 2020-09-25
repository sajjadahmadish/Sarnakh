package project

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.preference.PreferenceManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.viewpump.ViewPump
import org.greenrobot.eventbus.EventBus
import project.data.DataManager
import project.utils.AppLogger
import project.utils.localization.LocalizationApplicationDelegate
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {


    @Inject
    lateinit var dataManager: DataManager


    private var localizationDelegate = LocalizationApplicationDelegate(this)


    var session: Session? = null
        get() {
            if (field == null)
                field = object : Session {
                    override var isLoggedIn: Boolean
                        get() = dataManager.userLoggedInMode == DataManager.LoggedInMode.LOGGED_IN
                        set(value) {
                            if (value) {
                                dataManager.userLoggedInMode = DataManager.LoggedInMode.LOGGED_IN
                            } else {
                                dataManager.userLoggedInMode = DataManager.LoggedInMode.LOGGED_OUT
                            }
                        }

                    override var token: String
                        get() = dataManager.accessToken!!
                        set(value) {
                            dataManager.accessToken = value
                            dataManager.updateApiHeader(dataManager.currentUserId, value)
                        }

                    override var username: String
                        get() = dataManager.currentUserId!!
                        set(value) {
                            dataManager.currentUserId = value
                            dataManager.updateApiHeader(value, dataManager.accessToken!!)
                        }

                    override fun invalidate(fromApi: Boolean) {
                        // get called when user become logged out
                        // delete token and other user info
                        // (i.e: email, password)
                        // from the storage

                        // sending logged out event to it's listener
                        // i.e: Activity, Fragment, Service
                        EventBus.getDefault().post(LogoutEvent())
                    }

                }
            return field
        }

    class LogoutEvent(val flag: Boolean = true)


    companion object {
        fun get(activity: AppCompatActivity): App = activity.applicationContext as App
    }

    @Inject
    lateinit var viewPump: ViewPump

    override fun onCreate() {
        super.onCreate()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (dataManager.theme == DataManager.Theme.THEME_DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val ctx = applicationContext
        org.osmdroid.config.Configuration.getInstance()
            .load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))



        AppLogger.init()
        Fresco.initialize(this)
        ViewPump.init(viewPump)

        Stetho.initializeWithDefaults(this)

    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }


}