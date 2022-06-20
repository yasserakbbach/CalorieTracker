package com.yasserakbbach.calorietracker.repository

import com.yasserakbbach.tracker_domain.model.TrackableFood
import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate

class TrackerRepositoryFake: TrackerRepository {

    var shouldReturnError = false
    private val trackedFood = mutableListOf<TrackedFood>()
    var searchResults = listOf<TrackableFood>()
    private val getFoodsForDateFlow = MutableSharedFlow<List<TrackedFood>>(replay = 1)

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> =
        if(shouldReturnError) Result.failure(Throwable("Error"))
        else Result.success(searchResults)

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackedFood.add(food.copy(id = trackedFood.size.plus(1)))
        getFoodsForDateFlow.emit(trackedFood)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackedFood.remove(food)
        getFoodsForDateFlow.emit(trackedFood)
    }

    override fun getFoodsForDate(localeDate: LocalDate): Flow<List<TrackedFood>> =
        getFoodsForDateFlow
}