package project.ui.main.notification


import android.os.Bundle
import androidx.fragment.app.viewModels
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentNotificationBinding
import project.ui.base.BaseFragment
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment: BaseFragment<FragmentNotificationBinding, NotificationViewModel>(FragmentNotificationBinding::class.java) {


    override val viewModel: NotificationViewModel by viewModels { this.defaultViewModelProviderFactory }


    companion object {

        fun newInstance(): NotificationFragment {
            val args = Bundle()
            val fragment = NotificationFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






