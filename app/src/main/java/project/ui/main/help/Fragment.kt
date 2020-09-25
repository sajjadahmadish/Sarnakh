package project.ui.main.help


import android.os.Bundle
import androidx.fragment.app.viewModels
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentHelpBinding
import project.ui.base.BaseFragment
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HelpFragment: BaseFragment<FragmentHelpBinding, HelpViewModel>(FragmentHelpBinding::class.java) {


    override val viewModel: HelpViewModel by viewModels { this.defaultViewModelProviderFactory }


    companion object {

        fun newInstance(): HelpFragment {
            val args = Bundle()
            val fragment = HelpFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






