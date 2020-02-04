package project.ui.mission

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class MissionActivityModule {

    @Provides
    fun provideMissionViewModel(
        factory: ViewModelProviderFactory,
        activity: MissionActivity
    ): MissionViewModel =
        ViewModelProviders.of(activity, factory)[MissionViewModel::class.java]

}




