package project.ui.ticket

import android.os.Bundle
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityTicketBinding


class TicketActivity : BaseActivity<ActivityTicketBinding, TicketViewModel>(ActivityTicketBinding::class.java), TicketNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: TicketViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

    }

}




