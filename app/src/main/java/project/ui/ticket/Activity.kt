package project.ui.ticket

import android.os.Bundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding3.core.scrollChangeEvents
import com.jakewharton.rxbinding3.view.visibility
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityTicketBinding
import kotlinx.android.synthetic.main.activity_ticket.*
import project.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import ir.sinapp.sarnakh.databinding.ActivityLoginBinding


@AndroidEntryPoint
class TicketActivity:
    BaseActivity<ActivityTicketBinding, TicketViewModel>(ActivityTicketBinding::class.java),
    TicketNavigator {


    override val viewModel: TicketViewModel by viewModels { this.defaultViewModelProviderFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(ActivityTicketBinding.inflate(layoutInflater))
        viewModel.navigator = this


        binding.items.y = toolbar.height.toFloat() - binding.items.height
        binding.items.visibility().accept(false)


        viewModel += binding.nestedContent.scrollChangeEvents().subscribe {


            AppLogger.i(it.toString())
            var y: Float = (it.scrollY - 500).toFloat()

            if (y > 0)
                binding.items.visibility().accept(true)

            if (y > binding.items.height.toFloat()) y = binding.items.height.toFloat()
            else if (y < -20) y = -20f


            binding.items.y = (toolbar.height.toFloat() - binding.items.height + y + 20)

        }


        // y = toolbar


    }

    override fun goBack() {
        onBackPressed()
    }

}




