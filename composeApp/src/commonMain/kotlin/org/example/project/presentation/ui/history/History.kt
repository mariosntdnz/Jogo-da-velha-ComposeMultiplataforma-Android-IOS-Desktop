package org.example.project.presentation.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.domain.models.HistoryEntry
import org.example.project.domain.useCase.HistoryFilterType
import org.example.project.presentation.navigation.Screen
import org.example.project.presentation.ui.components.SimpleButton
import org.example.project.presentation.ui.components.SimpleTextCard
import org.example.project.presentation.viewmodels.HistoryEntryType
import org.example.project.presentation.viewmodels.HistoryViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HistoryScreen(
    navController: NavController,
    filterType: String
) {

    val viewModel = koinViewModel<HistoryViewModel>(
        parameters = {
            parametersOf(HistoryFilterType.valueOf(filterType))
        }

    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = .25f))
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        when {
            state.isLoading -> {
                LoadingScreen()
            }
            state.hasError -> {
                ErrorHistory(
                    msg = state.errorMsg,
                    onClick = {
                        navController.navigate(Screen.StartGame){
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            else -> {
                DefaultHistoryScreen(
                    list = state.historyItems,
                    onClick = { game ->
                        if (game.historyEntryType != HistoryEntryType.Finished) {
                            navController.navigate(
                                Screen.TicTacToe(
                                    gameId = game.gameId,
                                    gridLength = game.gridLength
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DefaultHistoryScreen(
    list: List<HistoryEntry>,
    onClick: (HistoryEntry) -> Unit
) {
    HistoryList(list, onClick)
}

@Composable
fun HistoryList(
    list: List<HistoryEntry>,
    onClick: (HistoryEntry) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item { Spacer(Modifier.height(16.dp)) }
        items(
            items = list,
            key = { it.gameId }
        ) { historyItem ->
            HistoryItem(
                historyItem = historyItem,
                onClick = {
                    onClick(historyItem)
                }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun HistoryItem(
    historyItem: HistoryEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(onClick = onClick)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = historyItem.gameHistoryTitle
            )
            Spacer(Modifier.height(8.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tabuleiro:"
                )
                Spacer(Modifier.weight(1f))
                SimpleTextCard(
                    text = historyItem.gridLengthLabel,
                    backgroundColor = Color.Green.copy(alpha = .25f),
                    textColor = Color.Black
                )
            }
            Spacer(Modifier.height(8.dp))
            when(historyItem.historyEntryType) {
                HistoryEntryType.Finished -> HistoryItemFinishedBody(
                    winner = historyItem.winnerName
                )
                HistoryEntryType.Continue -> HistoryItemContinueBody(
                    currentPlayer = historyItem.currentPlayer.name
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Situação:"
                )
                Spacer(Modifier.weight(1f))
                SimpleTextCard(
                    text = historyItem.historyEntryType.getLabel(),
                    backgroundColor = historyItem.historyEntryType.getColor(),
                    textColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun ColumnScope.HistoryItemFinishedBody(
    winner: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ganhador: "
        )
        Spacer(Modifier.weight(1f))
        SimpleTextCard(
            text = winner,
            backgroundColor = Color.Green.copy(alpha = .25f),
            textColor = Color.Black
        )
    }
}

@Composable
fun ColumnScope.HistoryItemContinueBody(
    currentPlayer: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Próxima jogada: "
        )
        Spacer(Modifier.weight(1f))
        SimpleTextCard(
            text = currentPlayer,
            backgroundColor = Color.Green.copy(alpha = .25f),
            textColor = Color.Black
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun BoxScope.ErrorHistory(
    msg: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(.75f),
            text = msg,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontSize = 32.sp,
            lineHeight = 36.sp
        )
        Spacer(Modifier.height(32.dp))
        SimpleButton(
            modifierButton = Modifier
                .fillMaxWidth(.9f),
            text = "Clique e inicie um novo jogo :)",
            fontSize = 16.sp,
            lineHeight = 24.sp,
            onClick = onClick
        )
    }
}
