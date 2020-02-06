package project.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import com.blankj.utilcode.util.StringUtils
import com.crashlytics.android.Crashlytics
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityLoginBinding
import project.ui.base.BaseActivity
import project.ui.main.MainActivity
import project.ui.profile.ProfileViewModel
import project.utils.AppLogger
import project.utils.Bungee
import project.utils.LocationUtils
import project.utils.launchActivity
import javax.inject.Inject


class LoginActivity :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::class.java),
    LoginNavigator {

    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: LoginViewModel


    override fun openMain() {
        launchActivity<MainActivity> {}
        Bungee.fade(this)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.navigator = this

        viewModel.registerSoftInput(window)


        viewModel += binding.buttonEn.clicks().subscribe {
            setLanguage("en", this)
        }

        viewModel += binding.buttonFa.clicks().subscribe {
            setLanguage("fa", this)
        }


        viewModel += binding.lytNext.clicks().subscribe {

            val user = viewModel.userName.get()
            val pass = viewModel.password.get()
            var flag = true

            if (StringUtils.isTrimEmpty(user)) {
                binding.userName.error = getString(R.string.username_cannot_empty)
                flag = false
            }

            if (StringUtils.isTrimEmpty(pass)) {
                binding.password.error = getString(R.string.password_cannot_empty)
                flag = false
            }

            if (!flag)
                return@subscribe

            showLoading()
            hideKeyboard()
            viewModel.login {
                hideLoading()
            }
        }

        viewModel.country = LocationUtils.getUserCountry(this)
        viewModel.setLang(currentLanguage.language)

    }


    override fun handleError(throwable: Throwable) {
        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.BOTTOM)
            .title(getString(R.string.error))
            .message(getString(R.string.check_your_connection))
            .backgroundColorRes(R.color.red_500)
            .dismissOnTapOutside()
            .icon(R.drawable.ic_error)
            .showIcon()
            .enableSwipeToDismiss()
            .duration(3000)
            .enterAnimation(
                FlashAnim.with(this)
                    .animateBar()
                    .duration(350)
                    .alpha()
                    .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(this)
                    .animateBar()
                    .duration(300)
                    .accelerateDecelerate()
            )
            .build().show()
        throwable.message?.let { AppLogger.i(it) }
    }


    override fun handleErrorApi(response: Int, message: String) {
        val er = when (response) {
            -5, -6 -> getString(R.string.user_or_pass_wrong)
            else -> getString(R.string._er_unknown)
        }

        Flashbar.Builder(this)
            .gravity(Flashbar.Gravity.BOTTOM)
            .title(getString(R.string.error))
            .message(er)
            .enableSwipeToDismiss()
            .dismissOnTapOutside()
            .duration(3000)
            .showIcon()
            .icon(R.drawable.ic_error)
            .backgroundColorRes(R.color.red_500)
            .enterAnimation(
                FlashAnim.with(this)
                    .animateBar()
                    .duration(350)
                    .alpha()
                    .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(this)
                    .animateBar()
                    .duration(300)
                    .accelerateDecelerate()
            )
            .build().show()
    }


}




