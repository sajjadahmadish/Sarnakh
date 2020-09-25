package project.ui.main.setting


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentSettingBinding
import org.greenrobot.eventbus.EventBus
import project.ui.base.BaseActivity
import project.ui.base.BaseFragment
import  project.utils.*
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>(FragmentSettingBinding::class.java) {


    override val viewModel: SettingViewModel by viewModels { this.defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.country = LocationUtils.getUserCountry(activity)
        viewModel.setLang((activity as BaseActivity<*, *>).currentLanguage.language)


        viewModel += binding.buttonEn.clicks().subscribe {
            EventBus.getDefault().post(EventLang("en"))
        }

        viewModel += binding.buttonFa.clicks().subscribe {
            EventBus.getDefault().post(EventLang("fa"))
        }


    }


    companion object {

        fun newInstance(): SettingFragment {
            val args = Bundle()
            val fragment = SettingFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






