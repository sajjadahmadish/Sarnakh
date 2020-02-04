package project.ui.login

import android.view.Window
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.NetworkUtils
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxkotlin.plusAssign
import project.data.DataManager
import project.di.module.ViewModelAssistedFactory
import project.ui.base.BaseViewModel
import project.utils.AppLogger
import project.utils.extension.forIo
import project.utils.extension.onUi
import project.utils.onChange
import project.utils.rx.SchedulerProvider

private const val STATE_USER = "state_user"
private const val STATE_PASS = "state_pass"


class LoginViewModel @AssistedInject constructor(
    @Assisted val handle: SavedStateHandle,
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {

    @AssistedInject.Factory
    interface Factory : ViewModelAssistedFactory<LoginViewModel>

    private var ip: String? = null
    var country: String? = null
    val language = ObservableInt(-1)
    val margin = ObservableInt(40)
    val userName = ObservableField("")
    val password = ObservableField("")


    init {
        userName.set(handle[STATE_USER] ?: "");
        password.set(handle[STATE_PASS] ?: "");



        userName.onChange { _, _ ->
            handle[STATE_USER] = userName.get()
        }

        password.onChange { _, _ ->
            handle[STATE_PASS] = password.get()
        }

    }





    fun login(function: () -> Unit) {
        try {
            ip = NetworkUtils.getIPAddress(true)
        } catch (e: Exception) {

        }

        val login = dataManager.login(
            userName.get() ?: "",
            password.get() ?: "",
            ip ?: "",
            (country ?: ""),
            DeviceUtils.getModel(),
            "Android",
            DeviceUtils.getSDKVersionCode().toString(),
            DeviceUtils.getSDKVersionName(),
            DeviceUtils.getUniqueDeviceId()
        )

        compositeDisposable += login
            .forIo(schedulerProvider)
            .onUi(schedulerProvider)
            .subscribe({
                isLoading.set(false)
                if (it.response < 0) {
                    navigator.handleErrorApi(it.response, it.message)
                } else {
                    dataManager.isRegister = false
                    dataManager.currentUserId = userName.get()
                    it.apply {
                        dataManager.updateBriefInfo(
                            firstName,
                            lastName,
                            gender == "m" || gender == "u"
                        )
                    }
                    navigator.openMain()
                }
                function.invoke()

            }, {
                function.invoke()
                isLoading.set(false)
                navigator.handleError(it)
                it.printStackTrace()
            })
    }


    fun registerSoftInput(window: Window) {
        KeyboardUtils.registerSoftInputChangedListener(
            window
        ) {
            AppLogger.i("$it")
            margin.set(if (it == 0) 40 else 0)
        }
    }

    fun setLang(language: String) {
        if (language == "fa")
            this.language.set(0)
        else if (language == "en")
            this.language.set(1)
    }


}




