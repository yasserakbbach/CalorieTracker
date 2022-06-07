package com.yasserakbbach.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.yasserakbbach.tracker_data.local.TrackerDatabase
import com.yasserakbbach.tracker_data.remote.OpenFoodApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOpenFoodApi(retrofit: Retrofit): OpenFoodApi =
        retrofit.create()

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase =
        Room.databaseBuilder(app, TrackerDatabase::class.java, "tracker_db")
            .build()
}