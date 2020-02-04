package project.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import project.App
import project.di.bulder.ActivityBuilderModule
import project.di.module.*
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidModule::class,
    RetrofitModule::class,
    AppModule::class,
    DataModule::class,
    ActivityBuilderModule::class,
    ViewModelAssistedFactoriesModule::class,
    AndroidInjectionModule::class
])
interface AppComponent:  AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>

}