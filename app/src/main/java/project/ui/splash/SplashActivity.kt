package project.ui.splash

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivitySplashBinding
import project.ui.base.BaseActivity
import project.ui.intro.IntroActivity
import project.ui.login.LoginActivity
import project.ui.main.MainActivity
import project.utils.Bungee
import project.utils.launchActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(ActivitySplashBinding::class.java),
    SplashNavigator {


    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: SplashViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        /** Hiding Title bar of this activity screen */
        window.requestFeature(Window.FEATURE_NO_TITLE)

        /** Making this activity, full screen */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        viewModel.decideNextActivity()

        //  logo animation
        viewModel += Observable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.title.alpha = 0.0f
                binding.title.visibility = View.VISIBLE
                binding.title.translationY = (-150).toFloat()
                binding.title.animate()
                    .setDuration(900)
                    .setInterpolator(LinearOutSlowInInterpolator())
                    .translationY(0f)
                    .alpha(1.0f)
                    .start()

                binding.myImageView.visibility = View.VISIBLE
                binding.myImageView.translationY = (0).toFloat()
                binding.myImageView.animate()
                    .setDuration(900)
                    .setInterpolator(LinearOutSlowInInterpolator())
                    .translationY(-60f)
                    .start()
            }

    }


    override fun openIntroActivity() {
        launchActivity<IntroActivity> {}
        Bungee.fade(this)
        finish()
    }

    override fun openMainActivity() {
        launchActivity<MainActivity> {}
        Bungee.fade(this)
        finish()
    }


}



