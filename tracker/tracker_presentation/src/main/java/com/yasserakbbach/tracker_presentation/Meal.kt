package com.yasserakbbach.tracker_presentation

import androidx.annotation.DrawableRes
import com.yasserakbbach.tracker_domain.model.MealType

data class Meal(
    val name: String,
    @DrawableRes val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false,
)
