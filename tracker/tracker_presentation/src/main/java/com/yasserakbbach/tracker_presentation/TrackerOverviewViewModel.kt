package com.yasserakbbach.tracker_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.navigation.Route
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
): ViewModel() {

    init {
        preferences.toggleShouldShowOnBoarding(false)
    }

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    fun onEvent(event: TrackerOverviewEvent) {
        when(event) {
            is TrackerOverviewEvent.OnAddFoodClick -> addFood(event)
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> deleteFood(event.trackedFood)
            TrackerOverviewEvent.OnNextDayClick -> nextDay()
            TrackerOverviewEvent.OnPreviousDayClick -> previousDay()
            is TrackerOverviewEvent.OnToggleMealClick -> toggleMeal(event)
        }
    }

    private fun addFood(event: TrackerOverviewEvent.OnAddFoodClick) {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    Route.SEARCH
                         .plus("/${event.meal.mealType.name}")
                         .plus("/${state.date.dayOfMonth}")
                         .plus("/${state.date.monthValue}")
                         .plus("/${state.date.year}")
                )
            )
        }
    }

    private fun deleteFood(trackedFood: TrackedFood) {
        viewModelScope.launch {
            trackerUseCases.deleteTrackedFoodUseCase(trackedFood)
            refreshFoods()
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDateUseCase(state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCases.calculateMealNutrientsUseCase(foods)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }

    private fun nextDay() {
        state = state.copy(
            date = state.date.plusDays(1)
        )
        refreshFoods()
    }

    private fun previousDay() {
        state = state.copy(
            date = state.date.minusDays(1)
        )
        refreshFoods()
    }

    private fun toggleMeal(event: TrackerOverviewEvent.OnToggleMealClick) {
        state = state.copy(
            meals = state.meals.map {
                if(it.name == event.meal.name) it.copy(isExpanded = !it.isExpanded)
                else it
            }
        )
    }
}