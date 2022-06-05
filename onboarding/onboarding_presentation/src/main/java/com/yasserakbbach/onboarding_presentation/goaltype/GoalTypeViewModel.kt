package com.yasserakbbach.onboarding_presentation.goaltype

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.model.GoalType
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.navigation.Route
import com.yasserakbbach.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalTypeViewModel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var selectedGoalType by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeSelected(goalType: GoalType) {
        selectedGoalType = goalType
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalType)
            _uiEvent.send(
                UiEvent.Navigate(Route.NUTRIENT_GOAL)
            )
        }
    }
}