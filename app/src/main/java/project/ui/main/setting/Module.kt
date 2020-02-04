package project.ui.main.setting

import dagger.Module
import dagger.Provides
import project.data.DataManager
import project.utils.rx.SchedulerProvider
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class SettingFragmentModule {

    @Provides
    fun provideSettingViewModel(
        factory: ViewModelProviderFactory,
        fragment: SettingFragment
    ): SettingViewModel =
        ViewModelProviders.of(fragment, factory)[SettingViewModel::class.java]

}




