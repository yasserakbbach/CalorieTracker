package com.yasserakbbach.onboarding_domain.usecase

import com.yasserakbbach.core.util.UiText
import com.yasserakbbach.core.R

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String,
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()

        if(carbsRatio == null || proteinRatio == null || fatRatio == null)
            return Result.Error(
                UiText.StringResource(R.string.error_invalid_values)
            )

        if(carbsRatio.plus(proteinRatio).plus(fatRatio) != 100)
            return Result.Error(
                UiText.StringResource(R.string.error_not_100_percent)
            )

        return Result.Success(
            carbsRatio = carbsRatio.div(100F),
            proteinRatio = proteinRatio.div(100F),
            fatRatio = fatRatio.div(100F),
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ): Result()

        data class Error(
            val message: UiText,
        ): Result()
    }
}