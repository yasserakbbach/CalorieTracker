package com.yasserakbbach.tracker_data.mapper

import com.yasserakbbach.tracker_data.local.entity.TrackedFoodEntity
import com.yasserakbbach.tracker_domain.model.MealType
import com.yasserakbbach.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood =
    TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        calories = calories,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType fromString type,
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        id = id,
    )

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity =
    TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        calories = calories,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        id = id,
    )