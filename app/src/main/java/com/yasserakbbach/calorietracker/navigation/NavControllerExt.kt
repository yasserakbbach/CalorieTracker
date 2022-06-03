package com.yasserakbbach.calorietracker.navigation

import androidx.navigation.NavController
import com.yasserakbbach.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}