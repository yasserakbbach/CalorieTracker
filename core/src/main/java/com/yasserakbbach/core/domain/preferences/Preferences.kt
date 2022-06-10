package com.yasserakbbach.core.domain.preferences

import com.yasserakbbach.core.domain.model.ActivityLevel
import com.yasserakbbach.core.domain.model.Gender
import com.yasserakbbach.core.domain.model.GoalType
import com.yasserakbbach.core.domain.model.UserInfo

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveGoalType(type: GoalType)
    fun saveActivityLevel(level: ActivityLevel)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): UserInfo

    fun toggleShouldShowOnBoarding(shouldShow: Boolean)
    fun getShouldShowOnBoarding(): Boolean

    companion object {
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val DEFAULT_AGE = -1
        const val KEY_WEIGHT = "weight"
        const val DEFAULT_WEIGHT = -1F
        const val KEY_HEIGHT = "height"
        const val DEFAULT_HEIGHT = -1
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val DEFAULT_CARB_RATIO = -1F
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val DEFAULT_PROTEIN_RATIO = -1F
        const val KET_FAT_RATIO = "fat_ratio"
        const val DEFAULT_FAT_RATIO = -1F
        const val CALORIE_TRACKER_PREFERENCES = "calorie_tracker_preferences"
        const val KEY_SHOULD_SHOW_ON_BOARDING = "should_show_on_boarding"
        const val DEFAULT_SHOULD_SHOW_ON_BOARDING = true
    }
}