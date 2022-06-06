package com.yasserakbbach.onboarding_presentation.nutrientgoal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.domain.usecase.FilterOutDigitsUseCase
import com.yasserakbbach.core.navigation.Route
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.onboarding_domain.usecase.ValidateNutrientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase,
): ViewModel() {

    var nutrientGoalState by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        nutrientGoalState = when(event) {
            is NutrientGoalEvent.OnCarbsRatioChange -> nutrientGoalState.copy(carbsRatio = filterOutDigitsUseCase(event.ratio))
            is NutrientGoalEvent.OnProteinsRatioChange -> nutrientGoalState.copy(proteinRatio = filterOutDigitsUseCase(event.ratio))
            is NutrientGoalEvent.OnFatsRatioChange -> nutrientGoalState.copy(fatRatio = filterOutDigitsUseCase(event.ratio))
            is NutrientGoalEvent.OnNextClick -> {
                onNextClick()
                nutrientGoalState
            }
        }
    }

    private fun onNextClick() {
        viewModelScope.launch {

            val result = validateNutrientsUseCase(
                carbsRatioText = nutrientGoalState.carbsRatio,
                proteinRatioText = nutrientGoalState.proteinRatio,
                fatRatioText = nutrientGoalState.fatRatio,
            )

            when(result) {
                is ValidateNutrientsUseCase.Result.Success -> saveNutrientsPreferencesAndNavigate(result)
                is ValidateNutrientsUseCase.Result.Error -> sendError(result)
            }
        }
    }

    private suspend fun saveNutrientsPreferencesAndNavigate(results: ValidateNutrientsUseCase.Result.Success) {
        preferences.apply {
            saveCarbRatio(results.carbsRatio)
            saveProteinRatio(results.proteinRatio)
            saveFatRatio(results.fatRatio)
        }
        _uiEvent.send(
            UiEvent.Navigate(Route.TRACKER_OVERVIEW)
        )
    }

    private suspend fun sendError(error: ValidateNutrientsUseCase.Result.Error) {
        _uiEvent.send(
            UiEvent.ShowSnackBar(error.message)
        )
    }
}