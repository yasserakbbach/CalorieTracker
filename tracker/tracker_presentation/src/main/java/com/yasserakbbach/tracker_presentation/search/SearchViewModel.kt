package com.yasserakbbach.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.core.domain.usecase.FilterOutDigitsUseCase
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.core.util.UiText
import com.yasserakbbach.tracker_domain.usecase.TrackerUseCases
import com.yasserakbbach.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnAmountForFoodChange -> onAmountForFoodChange(event)
            is SearchEvent.OnQueryChange -> onQueryChange(event)
            SearchEvent.OnSearch -> onSearch()
            is SearchEvent.OnSearchFocusChange -> onSearchFocusChange(event)
            is SearchEvent.OnToggleTrackableFood -> onToggleTrackableFood(event)
            is SearchEvent.OnTrackableFoodClick -> onTrackableFoodClick(event)
        }
    }

    private fun onAmountForFoodChange(event: SearchEvent.OnAmountForFoodChange) {
        state = state.copy(
            trackableFoodUiState = state.trackableFoodUiState.map {
                if(it.food == event.trackableFood)
                    it.copy(
                        amount = filterOutDigitsUseCase(event.amount),
                    )
                else
                    it
            }
        )
    }

    private fun onQueryChange(event: SearchEvent.OnQueryChange) {
        state = state.copy(
            query = event.query,
        )
    }

    private fun onSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoodUiState = emptyList(),
            )
            trackerUseCases.searchFoodUseCase(state.query)
                .onSuccess { listFoods ->
                    state = state.copy(
                        isSearching = false,
                        trackableFoodUiState = listFoods.map { TrackableFoodUiState(it) },
                        query = "",
                    )
                }.onFailure {
                    state = state.copy(
                        isSearching = false,
                    )
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            UiText.StringResource(R.string.error_something_went_wrong),
                        )
                    )
                }
        }
    }

    private fun onToggleTrackableFood(event: SearchEvent.OnToggleTrackableFood) {
        state = state.copy(
            trackableFoodUiState = state.trackableFoodUiState.map {
                if(it.food == event.trackableFood) it.copy(isExpanded = it.isExpanded.not()) else it
            }
        )
    }

    private fun onSearchFocusChange(event: SearchEvent.OnSearchFocusChange) {
        state = state.copy(
            isHintVisible = event.hasFocus.not() && state.query.isBlank(),
        )
    }

    private fun onTrackableFoodClick(event: SearchEvent.OnTrackableFoodClick) {
        viewModelScope.launch {
            val clickedFood = state.trackableFoodUiState.first { it.food == event.trackableFood }
            trackerUseCases.trackedFoodUseCase(
                food = clickedFood.food,
                amount = clickedFood.amount.toInt(),
                mealType = event.mealType,
                date = event.date,
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}