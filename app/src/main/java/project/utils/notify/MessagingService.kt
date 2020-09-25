package project.utils.notify

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus
import project.data.DataManager
import project.utils.EventNotify
import project.utils.rx.SchedulerProvider
import javax.inject.Inject


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MessagingService : FirebaseMessagingService() {

    companion object {
        const val TAG: String = "MyReceiver"
    }


    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var schedulerProvider: SchedulerProvider


    override fun onCreate() {
        super.onCreate()
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: ${dataManager.userLastName}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (remoteMessage.data.contains("notify")) {
                dataManager.notify = true
                EventBus.getDefault().post(EventNotify())
            }

        }

    }


}