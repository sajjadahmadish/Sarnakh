package project.ui.main.setting

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SettingFragmentProvider {

    @ContributesAndroidInjector(modules = [SettingFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): SettingFragment
}


