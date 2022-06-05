package com.yasserakbbach.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.domain.usecase.FilterOutDigitsUseCase
import com.yasserakbbach.core.navigation.Route
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.core.util.UiText
import com.yasserakbbach.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
): ViewModel() {

    var age by mutableStateOf(DEFAULT_AGE.toString())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeChange(age: String) {
        if(age.length <= MAX_DIGITS)
            this.age = filterOutDigitsUseCase(age)
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.error_age_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))
        }
    }

    companion object {
        const val DEFAULT_AGE = 18
        const val MAX_DIGITS = 3
    }
}