package project.ui.lucky

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class LuckyActivityModule {

    @Provides
    fun provideLuckyViewModel(
        factory: ViewModelProviderFactory,
        activity: LuckyActivity
    ): LuckyViewModel =
        ViewModelProviders.of(activity, factory)[LuckyViewModel::class.java]

}




