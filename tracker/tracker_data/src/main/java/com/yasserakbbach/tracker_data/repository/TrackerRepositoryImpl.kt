package com.yasserakbbach.tracker_data.repository

import com.yasserakbbach.tracker_data.local.TrackerDao
import com.yasserakbbach.tracker_data.mapper.toTrackableFood
import com.yasserakbbach.tracker_data.mapper.toTrackedFood
import com.yasserakbbach.tracker_data.mapper.toTrackedFoodEntity
import com.yasserakbbach.tracker_data.remote.OpenFoodApi
import com.yasserakbbach.tracker_domain.model.TrackableFood
import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
): TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> =
        try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize,
            )
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories = it.nutriments.carbohydrates100g.times(4F)
                            .plus(it.nutriments.proteins100g.times(4F))
                            .plus(it.nutriments.fat100g.times(9F))
                        val lowerBound = calculatedCalories * .99F
                        val upperBound = calculatedCalories * 1.01F
                        it.nutriments.energyKcal100g in lowerBound..upperBound
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localeDate: LocalDate): Flow<List<TrackedFood>> =
        dao.getFoodsForDate(
            localeDate.dayOfMonth,
            localeDate.monthValue,
            year = localeDate.year,
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }

}