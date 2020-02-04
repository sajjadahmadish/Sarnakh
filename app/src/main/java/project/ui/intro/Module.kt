package project.ui.intro

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class IntroActivityModule {

    @Provides
    fun provideIntroViewModel(
        factory: ViewModelProviderFactory,
        activity: IntroActivity
    ): IntroViewModel =
        ViewModelProviders.of(activity, factory)[IntroViewModel::class.java]

}




