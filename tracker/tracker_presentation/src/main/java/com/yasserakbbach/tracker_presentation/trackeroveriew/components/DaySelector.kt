package com.yasserakbbach.tracker_presentation.trackeroveriew.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yasserakbbach.core.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DaySelector(
    onNextDayClick: () -> Unit,
    onPreviousDayClick: () -> Unit,
    modifier: Modifier = Modifier,
    date: LocalDate,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousDayClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.previous_day),
            )
        }
        Text(
            text = parseDateText(date = date),
            style = MaterialTheme.typography.headlineSmall,
        )
        IconButton(onClick = onNextDayClick) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = stringResource(id = R.string.next_day),
            )
        }
    }
}

@Composable
fun parseDateText(date: LocalDate): String =
    LocalDate.now().run {
        when(date) {
            this -> stringResource(id = R.string.today)
            minusDays(1) -> stringResource(id = R.string.yesterday)
            plusDays(1) -> stringResource(id = R.string.tomorrow)
            else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
        }
    }