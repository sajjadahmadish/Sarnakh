package project.ui.main.wheel

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class WheelFragmentModule {

    @Provides
    fun provideWheelViewModel(
        factory: ViewModelProviderFactory,
        fragment: WheelFragment
    ): WheelViewModel =
        ViewModelProviders.of(fragment, factory)[WheelViewModel::class.java]

}




