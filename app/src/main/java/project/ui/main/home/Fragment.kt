package project.ui.main.home


import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import project.adapter.DetailSliderAdapter
import project.ui.base.BaseFragment
import project.utils.EventAr
import project.utils.EventMap
import project.utils.EventWheel
import project.utils.GlideImageLoadingService
import ss.com.bannerslider.Slider
import javax.inject.Inject


class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::class.java) {

    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: HomeViewModel


    companion object {

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Slider.init(GlideImageLoadingService(baseActivity))
        val list = listOf(
            "https://workshop.hostiran.net/file/2018/07/01-2.png",
            "https://banner-design.ir/wp-content/uploads/POST.jpg",
            "https://banner-design.ir/wp-content/uploads/nowruz-post-offer.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8dVYL-1SYWM9-XC-z0bEso6zEhqVVM0_1DZmHHuuTVWiy7Bmk",
            "https://banner-design.ir/wp-content/uploads/eid-post-blog-fetr96.jpg)"
        )
        binding.slider.setAdapter(DetailSliderAdapter(list))

        viewModel += binding.lytMap.clicks().subscribe {
            EventBus.getDefault().post(EventMap())
        }

        viewModel += binding.lytWheel.clicks().subscribe {
            EventBus.getDefault().post(EventWheel())
        }


    }

}






