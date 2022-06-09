package com.yasserakbbach.tracker_domain.di

import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.tracker_domain.repository.TrackerRepository
import com.yasserakbbach.tracker_domain.usecase.CalculateMealNutrientsUseCase
import com.yasserakbbach.tracker_domain.usecase.DeleteTrackedFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.GetFoodsForDateUseCase
import com.yasserakbbach.tracker_domain.usecase.SearchFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.TrackFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        trackerRepository: TrackerRepository,
        preferences: Preferences,
    ): TrackerUseCases =
        TrackerUseCases(
            trackedFoodUseCase = TrackFoodUseCase(trackerRepository),
            searchFoodUseCase = SearchFoodUseCase(trackerRepository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(trackerRepository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
}