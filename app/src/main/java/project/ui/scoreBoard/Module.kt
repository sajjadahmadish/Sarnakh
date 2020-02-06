package project.ui.scoreBoard

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class ScoreBoardActivityModule {

    @Provides
    fun provideScoreBoardViewModel(
        factory: ViewModelProviderFactory,
        activity: ScoreBoardActivity
    ): ScoreBoardViewModel =
        ViewModelProviders.of(activity, factory)[ScoreBoardViewModel::class.java]

}





