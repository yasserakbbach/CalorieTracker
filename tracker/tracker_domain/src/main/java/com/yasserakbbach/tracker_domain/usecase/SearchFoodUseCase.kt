package com.yasserakbbach.tracker_domain.usecase

import com.yasserakbbach.tracker_domain.model.TrackableFood
import com.yasserakbbach.tracker_domain.repository.TrackerRepository

class SearchFoodUseCase(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(
        query: String,
        page: Int = DEFAULT_PAGE,
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Result<List<TrackableFood>> =
        if(query.isBlank()) Result.success(emptyList())
        else {
            repository.runCatching {
                searchFood(query.trim(), page, pageSize)
            }.onFailure {
                Result.failure<List<TrackableFood>>(it)
            }.getOrThrow()
        }


    private companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 40
    }
}