package com.yasserakbbach.tracker_presentation.search

import com.yasserakbbach.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = "",
)
