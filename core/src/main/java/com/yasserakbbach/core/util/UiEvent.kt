package com.yasserakbbach.core.util

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackBar(val uiText: UiText): UiEvent()
}
