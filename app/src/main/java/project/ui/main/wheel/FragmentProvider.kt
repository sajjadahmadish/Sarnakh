package project.ui.main.wheel

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class WheelFragmentProvider {

    @ContributesAndroidInjector(modules = [WheelFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): WheelFragment
}


