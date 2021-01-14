package com.sample.kotlinboilerplate.di.module

import android.app.Application
import androidx.room.Room
import com.sample.kotlinboilerplate.BuildConfig
import com.sample.kotlinboilerplate.data.api.ApiHelper
import com.sample.kotlinboilerplate.data.api.ApiHelperImpl
import com.sample.kotlinboilerplate.data.api.ApiService
import com.sample.kotlinboilerplate.data.local.AppDatabase
import com.sample.kotlinboilerplate.data.local.DatabaseHelper
import com.sample.kotlinboilerplate.data.local.DatabaseHelperImpl
import com.sample.kotlinboilerplate.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application?): AppDatabase {
        return Room.databaseBuilder(application!!, AppDatabase::class.java, "Favorite Database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelper: DatabaseHelperImpl): DatabaseHelper = databaseHelper
}