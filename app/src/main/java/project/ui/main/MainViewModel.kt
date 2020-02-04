package project.ui.main

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.*
import project.utils.extension.forDatabase
import project.utils.extension.forIo
import project.utils.extension.onUi
import project.utils.rx.SchedulerProvider
import java.util.concurrent.TimeUnit


class MainViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    val tab = ObservableInt(1)
    val netView = ObservableBoolean(true)


    init {
        tab.onChange { _, _ ->
            when (tab.get()) {
                1, 2 -> netView.set(true)
                else -> netView.set(false)
            }
        }
    }


    fun join(v: View) {
        navigator.toggleFabMode()
        EventBus.getDefault().post(EventReminder())
    }

    fun create(v: View) {
        navigator.toggleFabMode()
        EventBus.getDefault().post(EventEvent())
    }

    fun updateHeader(function: (firstName: String, lastName: String, username: String, gender: Boolean) -> Unit): Observable<Unit> {
        return Observable.fromCallable {
            dataManager.apply {
                function.invoke(userFirstName!!, userLastName!!, currentUserId!!, gender!!)
            }
            return@fromCallable
        }
    }


    fun registerNotify(token: String) {
        val isRegister = dataManager.isRegister
        if (isRegister == null || isRegister == false) {
            compositeDisposable += dataManager
                .register(token)
                .subscribeOn(schedulerProvider.io())
                .doOnError {
                    AppLogger.i("cannot register application for this user")
                }.subscribe({
                    if (it.response == 1) {
                        dataManager.isRegister = true
                        AppLogger.i("register complete.  $token")
                    } else
                        AppLogger.i("register error : ${it.message}")
                }, {
                    it.printStackTrace()
                })
        }
    }


    //

}




