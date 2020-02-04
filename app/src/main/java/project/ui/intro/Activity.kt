package project.ui.intro


import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityIntroBinding
import kotlinx.android.synthetic.main.activity_splash.*
import project.ui.base.BaseActivity
import project.ui.login.LoginActivity
import project.ui.main.MainActivity
import project.utils.Bungee
import project.utils.extension.addOnPageChangeListener
import project.utils.launchActivity
import javax.inject.Inject


class IntroActivity :
    BaseActivity<ActivityIntroBinding, IntroViewModel>(ActivityIntroBinding::class.java),
    IntroNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: IntroViewModel

    @Inject
    lateinit var myViewPagerAdapter: MyViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        binding.viewPager.adapter = myViewPagerAdapter

        bottomProgressDots(0)
        binding.viewPager.addOnPageChangeListener { position ->
            viewModel.tab.set(position)
            bottomProgressDots(position)
        }


        viewModel += binding.btnSkip.clicks().subscribe {
            binding.viewPager.currentItem = viewModel.maxStep
        }

        viewModel += binding.btnGotIt.clicks().subscribe {
            openMainActivity()
        }

        viewModel += binding.account.clicks().subscribe {
            openLoginActivity()
        }
    }

    override fun openMainActivity() {
        viewModel.doNotShowAgain()
        launchActivity<MainActivity> {}
        Bungee.fade(this)
        finish()
    }

    override fun openLoginActivity() {
        viewModel.doNotShowAgain()
        launchActivity<LoginActivity> {}
        Bungee.fade(this)
        finish()
    }


    private fun bottomProgressDots(current_index: Int) {
        val dotsLayout = findViewById<LinearLayout>(R.id.layoutDots)
        val dots = arrayOfNulls<ImageView>(viewModel.maxStep)

        dotsLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val widthHeight = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!.setColorFilter(
                resources.getColor(R.color.overlay_dark_30),
                PorterDuff.Mode.SRC_IN
            )
            dotsLayout.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]!!.setColorFilter(
                resources.getColor(R.color.grey_10),
                PorterDuff.Mode.SRC_IN
            )
        }
    }


}




