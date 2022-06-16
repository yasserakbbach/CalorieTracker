package com.yasserakbbach.tracker_presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.yasserakbbach.core.util.UiEvent
import com.yasserakbbach.core_ui.LocalSpacing
import com.yasserakbbach.core.R
import com.yasserakbbach.tracker_domain.model.MealType
import com.yasserakbbach.tracker_presentation.search.components.SearchTextField
import com.yasserakbbach.tracker_presentation.search.components.TrackableFoodItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                UiEvent.NavigateUp -> onNavigateUp()
                is UiEvent.ShowSnackBar -> scope.launch {
                    snackBarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                    keyboardController?.hide()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceMedium),
        ) {
            Text(
                text = stringResource(id = R.string.add_meal, mealName),
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            SearchTextField(
                text = state.query,
                onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it))},
                onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
                onFocusChange = { viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused)) },
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(state.trackableFoodUiState) { trackableFoodUiState ->
                    TrackableFoodItem(
                        trackableFoodUiState = trackableFoodUiState,
                        onClick = { viewModel.onEvent(SearchEvent.OnToggleTrackableFood(trackableFood = trackableFoodUiState.food)) },
                        onAmountChange = {
                            viewModel.onEvent(SearchEvent.OnAmountForFoodChange(
                                trackableFood = trackableFoodUiState.food,
                                amount = it,
                            ))
                        },
                        onTrack = {
                              viewModel.onEvent(SearchEvent.OnTrackableFoodClick(
                                trackableFood = trackableFoodUiState.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth),
                            ))
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isSearching -> CircularProgressIndicator()
                state.trackableFoodUiState.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.no_results),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                else -> Unit
            }
        }
    }
}