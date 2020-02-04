package project.ui.main.notification

import dagger.Module
import dagger.Provides
import project.data.DataManager
import project.utils.rx.SchedulerProvider
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class NotificationFragmentModule {

    @Provides
    fun provideNotificationViewModel(
        factory: ViewModelProviderFactory,
        fragment: NotificationFragment
    ): NotificationViewModel =
        ViewModelProviders.of(fragment, factory)[NotificationViewModel::class.java]

}




