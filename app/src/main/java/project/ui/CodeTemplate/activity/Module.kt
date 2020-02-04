/*package ${packageName}

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class ${activityName}ActivityModule {

    @Provides
    fun provide${activityName}ViewModel(
        factory: ViewModelProviderFactory,
        activity: ${activityName}Activity
    ): ${activityName}ViewModel =
        ViewModelProviders.of(activity, factory)[${activityName}ViewModel::class.java]

}
*/




