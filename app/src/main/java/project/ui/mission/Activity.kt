package project.ui.mission

import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityMissionBinding
import project.adapter.DetailSliderAdapter
import project.ui.ar.ARActivity
import project.ui.base.BaseActivity
import project.utils.Bungee
import project.utils.GlideImageLoadingService
import project.utils.launchActivity
import ss.com.bannerslider.Slider
import xyz.hanks.library.bang.SmallBangView
import javax.inject.Inject


class MissionActivity :
    BaseActivity<ActivityMissionBinding, MissionViewModel>(ActivityMissionBinding::class.java),
    MissionNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MissionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        viewModel += binding.scan.clicks().subscribe {
            openARActivity()
        }

        Slider.init(GlideImageLoadingService(this))

        binding.slider.setAdapter(DetailSliderAdapter(viewModel.list))

        var counter = 0
        binding.likeHeart.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                if (counter != 0) {
                    counter--
                    binding.likeCounter.text = "$counter"
                }

            } else {
                counter++
                binding.likeCounter.text = "$counter"
                it.isSelected = true
                it as SmallBangView
                it.likeAnimation(object : AnimatorListenerAdapter() {})
            }

        }

    }

    private fun openARActivity() {
        launchActivity<ARActivity> {}
        Bungee.fade(this)
    }

    override fun goBack() {
        onBackPressed()
    }

}




