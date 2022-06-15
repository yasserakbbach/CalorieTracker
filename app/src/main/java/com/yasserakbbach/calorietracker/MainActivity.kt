package com.yasserakbbach.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yasserakbbach.calorietracker.navigation.navigate
import com.yasserakbbach.calorietracker.ui.theme.CalorieTrackerTheme
import com.yasserakbbach.core.navigation.Route
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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.WELCOME
                ) {
                    composable(Route.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.AGE) {
                        AgeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.GENDER) {
                        GenderScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.HEIGHT) {
                        HeightScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.WEIGHT) {
                        WeightScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.NUTRIENT_GOAL) {
                        NutrientGoalScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.ACTIVITY) {
                        ActivityScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.GOAL) {
                        GoalTypeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.TRACKER_OVERVIEW) {
                        TrackerOverviewScreen(onNavigate = navController::navigate)
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
                        val dayOfMoth = requireNotNull(it.arguments?.getInt("dayOfMoth"))
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