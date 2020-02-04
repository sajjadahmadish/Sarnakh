package project.ui.profile

import dagger.Module
import dagger.Provides
import androidx.lifecycle.ViewModelProviders
import project.ViewModelProviderFactory


@Module
class ProfileActivityModule {

    @Provides
    fun provideProfileViewModel(
        factory: ViewModelProviderFactory,
        activity: ProfileActivity
    ): ProfileViewModel =
        ViewModelProviders.of(activity, factory)[ProfileViewModel::class.java]

}




