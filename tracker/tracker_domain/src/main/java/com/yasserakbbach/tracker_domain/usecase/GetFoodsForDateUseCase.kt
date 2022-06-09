package com.yasserakbbach.tracker_domain.usecase

import com.yasserakbbach.tracker_domain.model.TrackedFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsForDateUseCase(
    private val repository: TrackerRepository,
) {

    operator fun invoke(localeDate: LocalDate): Flow<List<TrackedFood>> =
        repository.getFoodsForDate(localeDate)
}