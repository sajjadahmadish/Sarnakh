package project.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import project.di.module.ViewModelAssistedFactory;
import project.di.module.ViewModelKey;


@Module
public abstract class LoginSavedStateModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModelAssistedFactory<? extends ViewModel> bindFactory(LoginViewModel.Factory factory);

    @Binds
    abstract SavedStateRegistryOwner bindSavedStateRegistryOwner(LoginActivity owner);

    @Nullable
    @Provides
    static Bundle provideDefaultArgs() {
        return null;
    }

}
