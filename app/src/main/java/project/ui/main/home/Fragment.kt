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

        binding.slider.setAdapter(DetailSliderAdapter(viewModel.list))

        viewModel += binding.lytMap.clicks().subscribe {
            EventBus.getDefault().post(EventMap())
        }

        viewModel += binding.lytWheel.clicks().subscribe {
            EventBus.getDefault().post(EventWheel())
        }


    }

}






