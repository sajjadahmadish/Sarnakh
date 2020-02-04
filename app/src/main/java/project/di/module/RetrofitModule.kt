package project.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import project.data.remote.Api
import project.data.remote.ServiceApi
import project.utils.rx.SchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ClientModule::class])
class RetrofitModule {

    @Singleton
    @Provides
    fun serviceApi(retrofit: Retrofit)
            = ServiceApi(retrofit.create(Api::class.java))

    @Singleton
    @Provides
    fun getRetrofit(@Named("url") url: String,
                    client: OkHttpClient,
                    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) = Retrofit.Builder()
                        .addCallAdapterFactory(rxJava2CallAdapterFactory)
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(url)
                        .client(client)
                        .build()!!

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(schedulerProvider: SchedulerProvider) = RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io())!!

}