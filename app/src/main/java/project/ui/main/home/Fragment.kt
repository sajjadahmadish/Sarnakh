package project.ui.main.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import project.adapter.DetailSliderAdapter
import project.ui.base.BaseFragment
import project.utils.*
import ss.com.bannerslider.Slider
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment:
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::class.java) {


    override val viewModel: HomeViewModel by viewModels { this.defaultViewModelProviderFactory }

    @Inject
    lateinit var glideImageLoading: GlideImageLoadingService


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

        Slider.init(glideImageLoading)

        binding.slider.setAdapter(DetailSliderAdapter(viewModel.list))

        viewModel += binding.lytMap.clicks().subscribe {
            EventBus.getDefault().post(EventMap())
        }


        viewModel += binding.lytTicket.clicks().subscribe {
            EventBus.getDefault().post(EventTicket())
        }

        viewModel += binding.seeAll.clicks().subscribe {
            EventBus.getDefault().post(EventMissionList())
        }


    }

}






