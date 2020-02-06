package project.ui.ticket

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class TicketActivityModule {

    @Provides
    fun provideTicketViewModel(
        factory: ViewModelProviderFactory,
        activity: TicketActivity
    ): TicketViewModel =
        ViewModelProviders.of(activity, factory)[TicketViewModel::class.java]

}




