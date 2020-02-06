package project.ui.ticket

import android.os.Bundle
import com.jakewharton.rxbinding3.core.scrollChangeEvents
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityTicketBinding
import kotlinx.android.synthetic.main.activity_ticket.*
import project.utils.AppLogger


class TicketActivity : BaseActivity<ActivityTicketBinding, TicketViewModel>(ActivityTicketBinding::class.java), TicketNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: TicketViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this


        binding.items.y = toolbar.height.toFloat() - binding.items.height


        viewModel += binding.nestedContent.scrollChangeEvents().subscribe {

            viewModel.isItemsVisible.set(true)

            AppLogger.i(it.toString())
            val x = binding.items.height / 60
            var y:Float = (it.scrollY - 585).toFloat()
            if (y > 60) y = 60f
            else if (y < 0) y = 0f

            y *= x

            binding.items.y = (toolbar.height.toFloat() - binding.items.height + y)

        }


        // y = toolbar


    }

}




