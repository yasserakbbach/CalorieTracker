package com.yasserakbbach.calorietracker.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.yasserakbbach.core.data.preferences.DefaultPreferences
import com.yasserakbbach.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(Preferences.CALORIE_TRACKER_PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences =
        DefaultPreferences(sharedPreferences)
}