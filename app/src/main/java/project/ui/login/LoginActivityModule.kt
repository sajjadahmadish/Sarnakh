package project.ui.login

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import project.di.module.ViewModelFactory


@Module
class LoginActivityModule {

    @Provides
    fun provideLoginViewModel(
        factory: ViewModelFactory,
        activity: LoginActivity
    ): LoginViewModel =
        ViewModelProviders.of(activity, factory)[LoginViewModel::class.java]

}




