/*package ${packageName}

import android.os.Bundle
import project.ui.base.BaseActivity
import javax.inject.Inject

import ${PackageProjectName}.BR
import ${PackageProjectName}.databinding.Activity${activityName}Binding


class ${activityName}Activity : BaseActivity<Activity${activityName}Binding, ${activityName}ViewModel>(Activity${activityName}Binding::class.java), ${activityName}Navigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: ${activityName}ViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

    }

}

*/



