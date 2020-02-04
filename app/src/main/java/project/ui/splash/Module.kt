package project.ui.splash

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import project.ViewModelProviderFactory


@Module
class SplashActivityModule {

    @Provides
    fun provideSplashViewModel(
        factory: ViewModelProviderFactory,
        activity: SplashActivity
    ): SplashViewModel =
        ViewModelProviders.of(activity, factory)[SplashViewModel::class.java]


}




