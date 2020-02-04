package project.di.module;

import com.squareup.inject.assisted.dagger2.AssistedModule;
import dagger.Module;

@AssistedModule
@Module(includes = AssistedInject_ViewModelAssistedFactoriesModule.class)
public abstract class ViewModelAssistedFactoriesModule {
}