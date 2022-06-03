package com.yasserakbbach.core.data.preferences

import android.content.SharedPreferences
import com.yasserakbbach.core.domain.model.ActivityLevel
import com.yasserakbbach.core.domain.model.Gender
import com.yasserakbbach.core.domain.model.GoalType
import com.yasserakbbach.core.domain.model.UserInfo
import com.yasserakbbach.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences,
): Preferences {

    override fun saveGender(gender: Gender) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GOAL_TYPE, type.name)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, level.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KET_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo =
        sharedPreferences.run {
            UserInfo(
                gender = Gender fromString getString(Preferences.KEY_GENDER, Gender.MALE),
                age = getInt(Preferences.KEY_AGE, Preferences.DEFAULT_AGE),
                weight = getFloat(Preferences.KEY_WEIGHT, Preferences.DEFAULT_WEIGHT),
                height = getInt(Preferences.KEY_HEIGHT, Preferences.DEFAULT_HEIGHT),
                goalType = GoalType fromString getString(Preferences.KEY_GOAL_TYPE, GoalType.KEEP_WEIGHT),
                activityLevel = ActivityLevel fromString getString(Preferences.KEY_ACTIVITY_LEVEL, ActivityLevel.MEDIUM),
                carbRatio = getFloat(Preferences.KEY_CARB_RATIO, Preferences.DEFAULT_CARB_RATIO),
                proteinRatio = getFloat(Preferences.KEY_PROTEIN_RATIO, Preferences.DEFAULT_PROTEIN_RATIO),
                fatRatio = getFloat(Preferences.KET_FAT_RATIO, Preferences.DEFAULT_FAT_RATIO),
            )
        }
}