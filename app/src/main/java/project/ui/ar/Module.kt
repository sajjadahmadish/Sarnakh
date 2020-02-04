package project.ui.ar

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class ARActivityModule {

    @Provides
    fun provideARViewModel(
        factory: ViewModelProviderFactory,
        activity: ARActivity
    ): ARViewModel =
        ViewModelProviders.of(activity, factory)[ARViewModel::class.java]

}




