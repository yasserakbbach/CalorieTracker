package com.yasserakbbach.core.domain.model

sealed class GoalType(val name: String) {
    object LoseWeight: GoalType(LOSE_WEIGHT)
    object KeepWeight: GoalType(KEEP_WEIGHT)
    object GainWeight: GoalType(GAIN_WEIGHT)

    companion object {
        const val LOSE_WEIGHT = "lose_weight"
        const val KEEP_WEIGHT = "keep_weight"
        const val GAIN_WEIGHT = "gain_weight"

        infix fun fromString(name: String?): GoalType =
            when(name) {
                LOSE_WEIGHT -> LoseWeight
                KEEP_WEIGHT -> KeepWeight
                GAIN_WEIGHT -> GainWeight
                else -> throw IllegalArgumentException("Cannot get GoalType from $name")
            }
    }
}
