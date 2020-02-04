package project.ui.main.help


import android.os.Bundle
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentHelpBinding
import project.ui.base.BaseFragment
import javax.inject.Inject



class HelpFragment : BaseFragment<FragmentHelpBinding, HelpViewModel>(FragmentHelpBinding::class.java) {

    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: HelpViewModel


    companion object {

        fun newInstance(): HelpFragment {
            val args = Bundle()
            val fragment = HelpFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






