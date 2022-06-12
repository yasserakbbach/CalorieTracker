package com.yasserakbbach.tracker_presentation.trackeroveriew.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.yasserakbbach.core_ui.LocalSpacing
import com.yasserakbbach.core.R
import com.yasserakbbach.tracker_presentation.components.NutrientInfo
import com.yasserakbbach.tracker_presentation.components.UnitDisplay
import com.yasserakbbach.tracker_presentation.trackeroveriew.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes), 
                contentDescription = meal.name.asString(context),
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1F),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Icon(
                        imageVector = if(meal.isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = stringResource(id = if(meal.isExpanded) R.string.collapse else R.string.extend)
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = R.string.kcal),
                        amountTextSize = 30.sp,
                    )
                    Row {
                        NutrientInfo(
                            name = stringResource(id = R.string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = R.string.grams),
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = R.string.grams),
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = R.string.grams),
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}