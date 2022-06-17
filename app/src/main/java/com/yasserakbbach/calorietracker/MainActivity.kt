package com.yasserakbbach.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yasserakbbach.calorietracker.ui.theme.CalorieTrackerTheme
import com.yasserakbbach.core.domain.preferences.Preferences
import com.yasserakbbach.calorietracker.navigation.Route
import com.yasserakbbach.onboarding_presentation.activitylevel.ActivityScreen
import com.yasserakbbach.onboarding_presentation.age.AgeScreen
import com.yasserakbbach.onboarding_presentation.gender.GenderScreen
import com.yasserakbbach.onboarding_presentation.goaltype.GoalTypeScreen
import com.yasserakbbach.onboarding_presentation.height.HeightScreen
import com.yasserakbbach.onboarding_presentation.nutrientgoal.NutrientGoalScreen
import com.yasserakbbach.onboarding_presentation.weight.WeightScreen
import com.yasserakbbach.onboarding_presentation.welcome.WelcomeScreen
import com.yasserakbbach.tracker_presentation.search.SearchScreen
import com.yasserakbbach.tracker_presentation.trackeroveriew.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding = preferences.getShouldShowOnBoarding()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = if(shouldShowOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW,
                ) {
                    composable(Route.WELCOME) {
                        WelcomeScreen(
                            onNextClick = {
                                navController.navigate(Route.GENDER)
                            }
                        )
                    }
                    composable(Route.AGE) {
                        AgeScreen(
                            onNextClick = {
                                navController.navigate(Route.HEIGHT)
                            }
                        )
                    }
                    composable(Route.GENDER) {
                        GenderScreen(
                            onNextClick = {
                                navController.navigate(Route.AGE)
                            }
                        )
                    }
                    composable(Route.HEIGHT) {
                        HeightScreen(
                            onNextClick = {
                                navController.navigate(Route.WEIGHT)
                            }
                        )
                    }
                    composable(Route.WEIGHT) {
                        WeightScreen(
                            onNextClick = {
                                navController.navigate(Route.ACTIVITY)
                            }
                        )
                    }
                    composable(Route.NUTRIENT_GOAL) {
                        NutrientGoalScreen(
                            onNextClick = {
                                navController.navigate(Route.TRACKER_OVERVIEW)
                            }
                        )
                    }
                    composable(Route.ACTIVITY) {
                        ActivityScreen(
                            onNextClick = {
                                navController.navigate(Route.GOAL)
                            }
                        )
                    }
                    composable(Route.GOAL) {
                        GoalTypeScreen(
                            onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            }
                        )
                    }
                    composable(Route.TRACKER_OVERVIEW) {
                        TrackerOverviewScreen(
                            onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(Route.SEARCH.plus("/$mealName/$day/$month/$year"))
                            }
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
                        )
                    }
                }
            }
        }
    }
}