package project.ui.main.best


import android.os.Bundle
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentBestBinding
import project.ui.base.BaseFragment
import javax.inject.Inject



class BestFragment : BaseFragment<FragmentBestBinding, BestViewModel>(FragmentBestBinding::class.java) {

    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: BestViewModel


    companion object {

        fun newInstance(): BestFragment {
            val args = Bundle()
            val fragment = BestFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






