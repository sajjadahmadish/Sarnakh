package project.ui.main.notification

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class NotificationFragmentProvider {

    @ContributesAndroidInjector(modules = [NotificationFragmentModule::class])
    internal abstract fun provideOpenSourceFragmentFactory(): NotificationFragment
}


