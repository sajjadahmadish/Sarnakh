package project.ui.missionList

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class MissionListActivityModule {

    @Provides
    fun provideMissionListViewModel(
        factory: ViewModelProviderFactory,
        activity: MissionListActivity
    ): MissionListViewModel =
        ViewModelProviders.of(activity, factory)[MissionListViewModel::class.java]

}




