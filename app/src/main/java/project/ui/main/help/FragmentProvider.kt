package project.ui.main.help

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class HelpFragmentProvider {

    @ContributesAndroidInjector(modules = [HelpFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): HelpFragment
}


