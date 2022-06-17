package com.yasserakbbach.core.domain.model

sealed class ActivityLevel(val name: String) {
    object Low: ActivityLevel(LOW)
    object Medium: ActivityLevel(MEDIUM)
    object High: ActivityLevel(HIGH)

    companion object {
        const val LOW = "low"
        const val MEDIUM = "medium"
        const val HIGH = "high"

        infix fun fromString(name: String?): ActivityLevel =
            when(name?.lowercase()) {
                LOW -> Low
                MEDIUM -> Medium
                HIGH -> High
                else -> throw IllegalArgumentException("Cannot get ActivityLevel from $name")
            }
    }
}
