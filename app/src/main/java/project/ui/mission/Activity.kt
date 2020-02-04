package project.ui.mission

import android.animation.AnimatorListenerAdapter
import android.os.Bundle
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
        val list = listOf(
            "https://delta.ir/mag/wp-content/uploads/2019/05/%DA%AF%D9%84%D8%B3%D8%AA%D8%A7%D9%86.jpg",
            "https://www.snapptrip.com/blog/wp-content/uploads/2018/12/%DA%A9%D8%A7%D8%AE-%DA%AF%D9%84%D8%B3%D8%AA%D8%A7%D9%86-650x406.jpg",
            "https://images.kojaro.com/2016/6/12999f45-790d-4a70-8333-740ea8c30f35.jpg",
            "https://mashrooh.com/images/content/tourism/1395/22/Golestan-Palace.jpg"
        )
        binding.slider.setAdapter(DetailSliderAdapter(list))

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
                binding.likeCounter.text = "$counter";
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

}




