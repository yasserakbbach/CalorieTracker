package com.yasserakbbach.tracker_domain.usecase

import com.google.common.truth.Truth.assertThat
import com.yasserakbbach.core.domain.model.ActivityLevel
import com.yasserakbbach.core.domain.model.Gender
import com.yasserakbbach.core.domain.model.GoalType
import com.yasserakbbach.core.domain.model.UserInfo
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.tracker_domain.model.MealType
import com.yasserakbbach.tracker_domain.model.TrackedFood
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsUseCaseTest {

    private lateinit var calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 24,
            weight = 56F,
            height = 176,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.GainWeight,
            carbRatio = .4F,
            proteinRatio = .3F,
            fatRatio = .3F,
        )
        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = generateRandomTrackedFoods()

        val result = calculateMealNutrientsUseCase(trackedFoods)
        val breakFastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }
        val expectedCalories = trackedFoods.filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(breakFastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Carbs for dinner properly calculated`() {
        val trackedFoods = generateRandomTrackedFoods()

        val result = calculateMealNutrientsUseCase(trackedFoods)
        val dinnerCarbs = result.mealNutrients.values
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFoods.filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        assertThat(dinnerCarbs).isEqualTo(expectedCarbs)
    }

    private fun generateRandomTrackedFoods(): List<TrackedFood> =
        (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}