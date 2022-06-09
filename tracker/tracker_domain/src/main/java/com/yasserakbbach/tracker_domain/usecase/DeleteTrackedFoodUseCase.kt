package com.yasserakbbach.tracker_domain.usecase

import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(food: TrackedFood) {
        repository.deleteTrackedFood(food)
    }
}