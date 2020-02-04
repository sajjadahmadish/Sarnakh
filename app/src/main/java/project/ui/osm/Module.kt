package project.ui.osm

import dagger.Module
import dagger.Provides
import project.data.DataManager
import project.utils.rx.SchedulerProvider
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class OsmActivityModule {

    @Provides
    fun provideOsmViewModel(
        factory: ViewModelProviderFactory,
        activity: OsmActivity
    ): OsmViewModel =
        ViewModelProviders.of(activity, factory)[OsmViewModel::class.java]

}




