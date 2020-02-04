package project.ui.main.home

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): HomeFragment
}


