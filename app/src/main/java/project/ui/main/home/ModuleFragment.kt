package project.ui.main.home

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory
import project.utils.GlideImageLoadingService


@Module
class HomeFragmentModule {

    @Provides
    fun provideHomeViewModel(
        factory: ViewModelProviderFactory,
        fragment: HomeFragment
    ): HomeViewModel =
        ViewModelProviders.of(fragment, factory)[HomeViewModel::class.java]

    @Provides
    fun provideGlideImageLoader(fragment: HomeFragment) = GlideImageLoadingService(fragment.context)
}




