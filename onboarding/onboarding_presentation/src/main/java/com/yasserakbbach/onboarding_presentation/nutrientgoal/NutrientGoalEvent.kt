package com.yasserakbbach.onboarding_presentation.nutrientgoal

sealed class NutrientGoalEvent {
    data class OnCarbsRatioChange(val ratio: String): NutrientGoalEvent()
    data class OnProteinsRatioChange(val ratio: String): NutrientGoalEvent()
    data class OnFatsRatioChange(val ratio: String): NutrientGoalEvent()
    object OnNextClick: NutrientGoalEvent()
}
