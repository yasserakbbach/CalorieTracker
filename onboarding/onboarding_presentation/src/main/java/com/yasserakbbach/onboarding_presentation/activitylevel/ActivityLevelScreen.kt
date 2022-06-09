package com.yasserakbbach.onboarding_presentation.activitylevel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.core_ui.LocalSpacing
import com.yasserakbbach.core.R
import com.yasserakbbach.core.domain.model.ActivityLevel
import com.yasserakbbach.onboarding_presentation.components.ActionButton
import com.yasserakbbach.onboarding_presentation.components.SelectableButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ActivityScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ActivityLevelViewModel = hiltViewModel(),
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_activity_level),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.low),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Low,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelected(ActivityLevel.Low) },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.medium),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.Medium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelected(ActivityLevel.Medium) },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                SelectableButton(
                    text = stringResource(id = R.string.high),
                    isSelected = viewModel.selectedActivityLevel is ActivityLevel.High,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    selectedTextColor = Color.White,
                    onClick = { viewModel.onActivityLevelSelected(ActivityLevel.High) },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}