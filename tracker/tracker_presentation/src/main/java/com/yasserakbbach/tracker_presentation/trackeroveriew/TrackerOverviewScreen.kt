package com.yasserakbbach.tracker_presentation.trackeroveriew

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yasserakbbach.core_ui.LocalSpacing
import com.yasserakbbach.core.R
import com.yasserakbbach.tracker_presentation.trackeroveriew.components.AddButton
import com.yasserakbbach.tracker_presentation.trackeroveriew.components.DaySelector
import com.yasserakbbach.tracker_presentation.trackeroveriew.components.ExpandableMeal
import com.yasserakbbach.tracker_presentation.trackeroveriew.components.NutrientsHeader
import com.yasserakbbach.tracker_presentation.trackeroveriew.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium),
    ) {
        item {
            NutrientsHeader(state = state)
            DaySelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                onNextDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick) },
                onPreviousDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick) },
                date = state.date,
            )
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal)) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceSmall),
                ) {
                    val foods = state.trackedFoods.filter { it.mealType == meal.mealType }
                    foods.forEach { trackedFood ->
                        TrackedFoodItem(
                            trackedFood = trackedFood,
                            onDeleteClick = { viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(trackedFood)) },
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    AddButton(
                        text = stringResource(id = R.string.add_meal, meal.name.asString(context)),
                        onClick = {
                              onNavigateToSearch(
                                  meal.name.asString(context),
                                  state.date.dayOfMonth,
                                  state.date.monthValue,
                                  state.date.year,
                              )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}