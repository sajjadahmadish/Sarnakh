package project.ui.main.help

import dagger.Module
import dagger.Provides
import project.data.DataManager
import project.utils.rx.SchedulerProvider
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class HelpFragmentModule {

    @Provides
    fun provideHelpViewModel(
        factory: ViewModelProviderFactory,
        fragment: HelpFragment
    ): HelpViewModel =
        ViewModelProviders.of(fragment, factory)[HelpViewModel::class.java]

}




