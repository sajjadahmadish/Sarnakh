package project.ui.missionList

import android.os.Bundle
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.ActivityMissionListBinding


class MissionListActivity : BaseActivity<ActivityMissionListBinding, MissionListViewModel>(ActivityMissionListBinding::class.java), MissionListNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MissionListViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

    }

}




