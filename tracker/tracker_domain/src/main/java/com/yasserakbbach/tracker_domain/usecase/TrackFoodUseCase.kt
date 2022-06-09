package com.yasserakbbach.tracker_domain.usecase

import com.yasserakbbach.tracker_domain.model.MealType
import com.yasserakbbach.tracker_domain.model.TrackableFood
import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / 100F) * amount).roundToInt(),
                protein = ((food.proteinPer100g / 100F) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100F) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
                calories = ((food.caloriesPer100g / 100F) * amount).roundToInt(),
            )
        )
    }
}