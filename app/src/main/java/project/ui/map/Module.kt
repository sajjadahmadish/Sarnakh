package project.ui.map

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import project.ViewModelProviderFactory


@Module
class MapActivityModule {

    @Provides
    fun provideMapViewModel(
        factory: ViewModelProviderFactory,
        activity: MapActivity
    ): MapViewModel =
        ViewModelProviders.of(activity, factory)[MapViewModel::class.java]

}




