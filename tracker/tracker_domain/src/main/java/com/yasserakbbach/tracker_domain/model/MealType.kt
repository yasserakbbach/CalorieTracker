package com.yasserakbbach.tracker_domain.model

sealed class MealType(val name: String) {

    object Breakfast: MealType(BREAKFAST)
    object Lunch: MealType(LUNCH)
    object Dinner: MealType(DINNER)
    object Snack: MealType(SNACK)

    companion object {

        private const val BREAKFAST = "breakfast"
        private const val LUNCH = "lunch"
        private const val DINNER = "dinner"
        private const val SNACK = "snack"

        infix fun fromString(name: String): MealType =
            when(name.lowercase()) {
                BREAKFAST -> Breakfast
                LUNCH -> Lunch
                DINNER -> Dinner
                SNACK -> Snack
                else -> throw IllegalArgumentException("Unable to get $name meal type")
            }
    }
}
