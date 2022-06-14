package com.yasserakbbach.tracker_presentation.search

import com.yasserakbbach.tracker_domain.model.MealType
import com.yasserakbbach.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    object OnSearch : SearchEvent()
    data class OnToggleTrackableFood(val trackableFood: TrackableFood) : SearchEvent()
    data class OnAmountForFoodChange(val trackableFood: TrackableFood, val amount: Int) : SearchEvent()
    data class OnTrackableFoodClick(
        val trackableFood: TrackableFood,
        val mealType: MealType,
        val date: LocalDate,
    ) : SearchEvent()
    data class OnSearchFocusChange(val hasFocus: Boolean) : SearchEvent()
}
