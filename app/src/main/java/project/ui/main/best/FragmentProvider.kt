package project.ui.main.best

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BestFragmentProvider {

    @ContributesAndroidInjector(modules = [BestFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): BestFragment
}


