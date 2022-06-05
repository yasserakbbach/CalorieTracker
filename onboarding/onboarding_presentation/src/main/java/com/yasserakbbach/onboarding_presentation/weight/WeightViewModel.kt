package com.yasserakbbach.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.core.util.UiText
import com.yasserakbbach.core.R
import com.yasserakbbach.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var weight by mutableStateOf(DEFAULT_WIGHT.toString())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightChange(weight: String) {
        if(weight.length <= MAX_WEIGHT_DIGITS)
            this.weight = weight
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.error_weight_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveWeight(weightNumber)
            _uiEvent.send(
                UiEvent.Navigate(Route.ACTIVITY)
            )
        }
    }

    companion object {
        const val DEFAULT_WIGHT = 50.0
        const val MAX_WEIGHT_DIGITS = 5
    }
}