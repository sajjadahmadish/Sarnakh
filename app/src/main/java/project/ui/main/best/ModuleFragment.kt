package project.ui.main.best

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class BestFragmentModule {

    @Provides
    fun provideBestViewModel(
        factory: ViewModelProviderFactory,
        fragment: BestFragment
    ): BestViewModel =
        ViewModelProviders.of(fragment, factory)[BestViewModel::class.java]

}




