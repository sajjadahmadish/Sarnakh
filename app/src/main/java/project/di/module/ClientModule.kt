package project.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import ir.sinapp.sarnakh.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.App
import project.data.remote.ApiInfo
import project.utils.AppLogger
import project.utils.okHttp.AuthorizationInterceptor
import project.utils.okHttp.TokenRenewInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ClientModule {

    @Singleton
    @Provides
    fun getClient(
        context: Context,
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("timeOut") timeOut: Long,
        @ApiInfo apiKey: String
    ) = OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .addInterceptor(AuthorizationInterceptor(apiKey, (context as App).session))
        .addInterceptor(TokenRenewInterceptor(context.session!!))
        .build()

    @Singleton
    @Provides
    fun getCache(file: File, @Named("maxSize") maxsize: Int) = Cache(file, maxsize.toLong())

    @Singleton
    @Provides
    fun getFile(context: Context) = File(context.cacheDir, "OkHttpClient")

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = when (BuildConfig.DEBUG) {
        true -> {
            val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    AppLogger.i(message)
                }
            })
            httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC }
        }
        false -> {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE }
        }
    }


}