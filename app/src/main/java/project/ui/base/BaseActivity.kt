package project.ui.base

import ir.sinapp.sarnakh.BR
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LanguageUtils
import com.novoda.merlin.Endpoint
import com.novoda.merlin.MerlinFlowable
import com.novoda.merlin.MerlinsBeard
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import project.App
import project.Session
import project.ui.login.LoginActivity
import project.utils.*
import project.utils.localization.LocalizationActivityDelegate
import project.utils.network.NetworkStatusDisplayer
import project.utils.network.hasInternetAccess
import java.util.*
import kotlin.coroutines.resume


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>>(private val bindingClass: Class<T>) :
    AppCompatActivity(),
    BaseFragment.Callback {

    lateinit var networkStatusDisplayer: NetworkStatusDisplayer
    private var merlinsBeard: MerlinsBeard? = null

    private var viewDataBinding: T? = null

    lateinit var binding: T

    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private var mProgressDialog: ProgressDialog? = null


    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V


    private lateinit var localizationActivityDelegate: LocalizationActivityDelegate

    val currentLanguage: Locale
        get() = localizationActivityDelegate.getLanguage(this)


    protected val session: Session?
        get() = App.get(this).session


    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {

        localizationActivityDelegate.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CommonUtils.updateLanguage(currentLanguage, this)
        }

        super.onCreate(savedInstanceState)

        merlinsBeard = MerlinsBeard.Builder()
            .withEndpoint(Endpoint.from("http://classmate.ahmadish.ir/generate_204"))
            .build(this)
        networkStatusDisplayer = NetworkStatusDisplayer(resources, merlinsBeard)


    }


    override fun attachBaseContext(newBase: Context) {
        localizationActivityDelegate = LocalizationActivityDelegate(this)

        val baseContext = localizationActivityDelegate.attachBaseContext(newBase)
        super.attachBaseContext(ViewPumpContextWrapper.wrap(baseContext))
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }

    fun showKeyboard() {
        KeyboardUtils.showSoftInput(binding.root)
    }


    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }


    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }


    @Suppress("UNUSED_PARAMETER")
    @Subscribe
    fun openActivityOnTokenExpire(logoutEvent: App.LogoutEvent) {
        if (logoutEvent.flag) viewModel.logout(true)
        launchActivity<LoginActivity> { }
        Bungee.swipeLeft(this)
        finish()
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


    fun initBinding(viewBinding: T, br: Int = BR.viewModel) {
        binding = viewBinding
        setContentView(binding.root)
        binding.setVariable(br, viewModel)
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        localizationActivityDelegate.onResume(this)

        viewModel.setChecker(::checkConnected)
        val merlinDisposable = MerlinFlowable.from(this)
            .distinctUntilChanged()
            .subscribe {
                if (it.isAvailable) {
                    AppLogger.i("NetworkLog :  Available")
                    viewModel.networkStatus.set(BaseViewModel.NetStatus.CONNECTING)
                    viewModel.checkConnected()
                } else {
                    retry = false
                    AppLogger.i("NetworkLog :  Unavailable")
                    viewModel.networkStatus.set(BaseViewModel.NetStatus.DISCONNECTED)
                }
            }
        viewModel += merlinDisposable
    }

    private var retry = true
    private suspend fun checkConnected() {
        retry = true
        do {
            if (viewModel.networkStatus.get() == BaseViewModel.NetStatus.CONNECTED)
                delay(6000)
            AppLogger.i("NetworkLog :  checking")
            waitForInternet()
        } while (retry)
        AppLogger.i("NetworkLog :  end of checking")
    }

    private suspend fun waitForInternet(): Unit = suspendCancellableCoroutine { continuation ->
        merlinsBeard!!.hasInternetAccess(3) {
            if (it) {
                AppLogger.i("NetworkLog :  checked and  $it")
                viewModel.networkStatus.set(BaseViewModel.NetStatus.CONNECTED)
            } else {
                if (viewModel.networkStatus.get() == BaseViewModel.NetStatus.CONNECTED)
                    viewModel.networkStatus.set(BaseViewModel.NetStatus.CONNECTING)
            }
            continuation.resume(Unit)
        }
    }

    override fun getApplicationContext(): Context {
        return localizationActivityDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationActivityDelegate.getResources(super.getResources())
    }


    protected fun setLanguage(language: String, activity: AppCompatActivity) {
        CommonUtils.updateLanguage(Locale(language), this)
        localizationActivityDelegate.setLanguage(this, language)
        LanguageUtils.applyLanguage(Locale(language), activity.javaClass.name)
        Bungee.fade(this)
        AppLogger.i("change lang to fa")
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}

