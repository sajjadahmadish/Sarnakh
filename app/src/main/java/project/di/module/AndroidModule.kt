package project.di.module

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import dagger.Module
import dagger.Provides
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.sinapp.sarnakh.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import project.App
import project.utils.rx.AppSchedulerProvider
import project.utils.rx.SchedulerProvider
import javax.inject.Singleton


@Module
class AndroidModule {

    @Singleton
    @Provides
    fun provideContext(app: App): Context = app.applicationContext

    @Singleton
    @Provides
    fun resource(app: App): Resources = app.resources!!


    @Provides
    fun getHandler() = Handler()

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


}
