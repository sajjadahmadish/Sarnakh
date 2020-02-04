package project.ui.base


import androidx.databinding.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import project.App
import project.data.DataManager
import project.utils.AppLogger
import project.utils.change
import project.utils.extension.forIo
import project.utils.extension.onUi
import project.utils.rx.SchedulerProvider
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


abstract class BaseViewModel<N>(
    val dataManager: DataManager,
    val schedulerProvider: SchedulerProvider
) : ViewModel(), Observable {


    // region  Network

    enum class NetStatus {
        DISCONNECTED, CONNECTING, CONNECTED
    }

    val networkStatus = ObservableField<NetStatus>(NetStatus.DISCONNECTED)

    private lateinit var checker: (suspend () -> Unit)
    fun setChecker(checker: suspend () -> Unit) {
        this.checker = checker
    }

    fun checkConnected() {
        viewModelScope.launch {
            withContext(IO) {
                checker.invoke()
            }
        }
    }

    fun withInternet(callback: () -> Boolean) {
        if (networkStatus.get() == NetStatus.CONNECTED)
            callback.invoke()
        else
            networkStatus.change {
                if (it == NetStatus.CONNECTED) {
                    callback.invoke()
                } else
                    false
            }
    }


    // endregion


    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val isLoading = ObservableBoolean(false)

    private var mNavigator: WeakReference<N>? = null

    var navigator: N
        get() = mNavigator!!.get()!!
        set(navigator) {
            this.mNavigator = WeakReference(navigator)
        }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable += disposable
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


    // DataBinding
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }


    fun logout(flag: Boolean = false) {
        dataManager.setUserAsLoggedOut()
        compositeDisposable += dataManager.truncateMarker()
            .forIo(schedulerProvider)
            .onUi(schedulerProvider)
            .subscribe({
                AppLogger.i("database : logout success");
            }, {
                AppLogger.i("database : logout failed");
            })

        if (!flag)
            this += dataManager.logout().subscribe({
                AppLogger.i("api : logout success");
            }, {
                AppLogger.i("api : logout failed");
            })

        EventBus.getDefault().post(App.LogoutEvent(false))
    }


    operator fun plusAssign(disposable: Disposable) {
        compositeDisposable += disposable
    }


}
