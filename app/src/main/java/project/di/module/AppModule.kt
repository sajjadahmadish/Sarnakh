package project.di.module

import dagger.Module
import dagger.Provides
import ir.sinapp.sarnakh.BuildConfig
import project.common.Config
import project.data.remote.ApiInfo
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @Named(value = "url")
    fun url() = BuildConfig.API_URL

    @Singleton
    @Provides
    @Named(value = "maxSize")
    fun maxSize() = Config.maxSize

    @Singleton
    @Provides
    @CacheInfo
    fun maxRxCacheSize() = Config.maxRxCacheSize

    @Singleton
    @Provides
    @Named(value = "timeOut")
    fun timeOut() = Config.timeOut

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String = Config.DB_NAME

    @Provides
    @ApiInfo
    fun provideApiKey(): String = BuildConfig.API_KEY


}