package project.ui.main.home

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class HomeFragmentModule {

    @Provides
    fun provideHomeViewModel(
        factory: ViewModelProviderFactory,
        fragment: HomeFragment
    ): HomeViewModel =
        ViewModelProviders.of(fragment, factory)[HomeViewModel::class.java]

}




