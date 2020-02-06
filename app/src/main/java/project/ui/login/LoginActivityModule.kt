package project.ui.login

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import project.ViewModelProviderFactory


@Module
class LoginActivityModule {

    @Provides
    fun provideLoginViewModel(
        factory: ViewModelProviderFactory,
        activity: LoginActivity
    ): LoginViewModel =
        ViewModelProviders.of(activity, factory)[LoginViewModel::class.java]

}




