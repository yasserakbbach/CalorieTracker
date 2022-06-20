package com.yasserakbbach.calorietracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.common.truth.Truth.assertThat
import com.yasserakbbach.calorietracker.navigation.Route
import com.yasserakbbach.calorietracker.repository.TrackerRepositoryFake
import com.yasserakbbach.calorietracker.ui.theme.CalorieTrackerTheme
import com.yasserakbbach.core.domain.model.ActivityLevel
import com.yasserakbbach.core.domain.model.Gender
import com.yasserakbbach.core.domain.model.GoalType
import com.yasserakbbach.core.domain.model.UserInfo
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.core.domain.usecase.FilterOutDigitsUseCase
import com.yasserakbbach.tracker_domain.model.TrackableFood
import com.yasserakbbach.tracker_domain.usecase.CalculateMealNutrientsUseCase
import com.yasserakbbach.tracker_domain.usecase.DeleteTrackedFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.GetFoodsForDateUseCase
import com.yasserakbbach.tracker_domain.usecase.SearchFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.TrackFoodUseCase
import com.yasserakbbach.tracker_domain.usecase.TrackerUseCases
import com.yasserakbbach.tracker_presentation.search.SearchScreen
import com.yasserakbbach.tracker_presentation.search.SearchViewModel
import com.yasserakbbach.tracker_presentation.trackeroveriew.TrackerOverviewScreen
import com.yasserakbbach.tracker_presentation.trackeroveriew.TrackerOverviewViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt
import kotlin.random.Random

@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var trackerRepositoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        preferences = mockk(relaxed = true)
        every { preferences.loadUserInfo() } returns generateRandomUserInfo()
        trackerRepositoryFake = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackedFoodUseCase = TrackFoodUseCase(trackerRepositoryFake),
            searchFoodUseCase = SearchFoodUseCase(trackerRepositoryFake),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepositoryFake),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(trackerRepositoryFake),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences),
        )
        trackerOverviewViewModel = TrackerOverviewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases,
        )
        searchViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterOutDigitsUseCase = FilterOutDigitsUseCase(),
        )

        composeRule.setContent {
            navController = rememberNavController()
            CalorieTrackerTheme {
                NavHost(
                    navController = navController,
                    startDestination = Route.TRACKER_OVERVIEW,
                ) {
                    composable(Route.TRACKER_OVERVIEW) {
                        TrackerOverviewScreen(
                            onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(Route.SEARCH.plus("/$mealName/$day/$month/$year"))
                            },
                            viewModel = trackerOverviewViewModel,
                        )
                    }
                    composable(
                        route = Route.SEARCH.plus("/{mealName}/{dayOfMonth}/{month}/{year}"),
                        arguments = listOf(
                            navArgument("mealName") {
                                type = NavType.StringType
                            },
                            navArgument("dayOfMonth") {
                                type = NavType.IntType
                            },
                            navArgument("month") {
                                type = NavType.IntType
                            },
                            navArgument("year") {
                                type = NavType.IntType
                            },
                        )
                    ) {
                        val mealName = requireNotNull(it.arguments?.getString("mealName"))
                        val dayOfMoth = requireNotNull(it.arguments?.getInt("dayOfMonth"))
                        val month = requireNotNull(it.arguments?.getInt("month"))
                        val year = requireNotNull(it.arguments?.getInt("year"))
                        SearchScreen(
                            mealName = mealName,
                            dayOfMonth = dayOfMoth,
                            month = month,
                            year = year,
                            onNavigateUp = { navController.navigateUp() },
                            viewModel = searchViewModel,
                        )
                    }
                }
            }
        }
    }

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientsProperlyCalculated() {
        trackerRepositoryFake.searchResults = listOf(
            TrackableFood(
                name = "banana",
                imageUrl = null,
                caloriesPer100g = 150,
                proteinPer100g = 5,
                carbsPer100g = 50,
                fatPer100g = 1,
            )
        )
        val addedAmount = 150
        val expectedCalories = (1.5F * 150).roundToInt()
        val expectedCarbs = (1.5F * 50).roundToInt()
        val expectedProtein = (1.5F * 5).roundToInt()
        val expectedFat = (1.5F * 1).roundToInt()

        composeRule.onNodeWithText("Add Breakfast")
            .assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Breakfast")
            .performClick()
        composeRule.onNodeWithText("Add Breakfast")
            .assertIsDisplayed()
        composeRule.onNodeWithText("Add Breakfast")
            .performClick()

        assertThat(
            navController.currentDestination
                ?.route
                ?.startsWith(Route.SEARCH)
        ).isTrue()

        composeRule.onNodeWithTag("search_text_field")
            .performTextInput("banana")
        composeRule.onNodeWithContentDescription("Search…")
            .performClick()
        composeRule.onNodeWithText("Carbs")
            .performClick()
        composeRule.onNodeWithContentDescription("Amount")
            .performTextInput(addedAmount.toString())
        composeRule.onNodeWithContentDescription("Track")
            .performClick()

        assertThat(
            navController.currentDestination
                ?.route
                ?.startsWith(Route.TRACKER_OVERVIEW)
        ).isTrue()

        composeRule.onAllNodesWithText(expectedCarbs.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(expectedCalories.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(expectedProtein.toString())
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(expectedFat.toString())
            .onFirst()
            .assertIsDisplayed()
    }

    private fun generateRandomUserInfo(): UserInfo =
        UserInfo(
            gender = Gender.fromString(listOf(Gender.MALE, Gender.FEMALE).random()),
            age = Random.nextInt(18, 100),
            weight = Random.nextFloat(),
            height = Random.nextInt(50, 250),
            goalType = GoalType.fromString(
                listOf(
                    GoalType.GAIN_WEIGHT,
                    GoalType.LOSE_WEIGHT,
                    GoalType.KEEP_WEIGHT
                ).random()
            ),
            activityLevel = ActivityLevel.fromString(
                listOf(
                    ActivityLevel.HIGH,
                    ActivityLevel.MEDIUM,
                    ActivityLevel.LOW
                ).random()
            ),
            carbRatio = .4F,
            proteinRatio = .3F,
            fatRatio = .3F,
        )
}