package project.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import project.data.AppDataManager
import project.data.DataManager
import project.data.local.db.AppDatabase
import project.data.local.db.AppDbHelper
import project.data.local.db.DbHelper
import project.data.local.preferences.AppPreferenceHelper
import project.data.local.preferences.PreferenceHelper
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper = appDbHelper

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String,@ApplicationContext context: Context)
            : AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration().build()

}


