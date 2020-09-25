package project.di

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.sinapp.sarnakh.BuildConfig
import ir.sinapp.sarnakh.R
import project.App
import project.common.Config
import project.data.remote.ApiInfo
import project.utils.rx.AppSchedulerProvider
import project.utils.rx.SchedulerProvider
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AndroidModule {


    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


    @Provides
    @Singleton
    fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/IRANYekanMobileRegular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .setFontMapper { font -> font }
            .build()
    }

    @Provides
    @Singleton
    fun provideCalligraphyInterceptor(calligraphyConfig: CalligraphyConfig) =
        CalligraphyInterceptor(calligraphyConfig)

    @Provides
    @Singleton
    fun provideViewPump(calligraphyInterceptor: CalligraphyInterceptor): ViewPump =
        ViewPump.builder().addInterceptor(calligraphyInterceptor).build()


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
