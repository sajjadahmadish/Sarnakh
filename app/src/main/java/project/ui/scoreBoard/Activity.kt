package project.ui.scoreBoard

import android.os.Bundle
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityScoreBoardBinding


class ScoreBoardActivity : BaseActivity<ActivityScoreBoardBinding, ScoreBoardViewModel>(ActivityScoreBoardBinding::class.java), ScoreBoardNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: ScoreBoardViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

    }

}