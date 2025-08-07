package org.example.project.ui.tictactoe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.getPlatform
import org.example.project.utils.clickableWithoutAnimation
import org.example.project.viewmodels.TicTacToeItem
import org.example.project.viewmodels.TicTacToeViewModel
import org.koin.compose.viewmodel.koinViewModel

data class TicTacToeState(
    val gridLength: Int,
    val currentGrid: HashMap<Int, List<TicTacToeItem>>
) {
    val currentGridList get() = currentGrid.values.flatten()
}


@Composable
fun TicTacToeScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<TicTacToeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getPlatform().name,
            fontSize = 32.sp,
            color = Color.Blue,
            modifier = Modifier.clickableWithoutAnimation(onClick = navController::navigateUp)
        )

        TicTacToeGrid(
            qtyCells = state.gridLength,
            list = state.currentGridList
        )

    }
}

@Composable
fun TicTacToeGrid(
    qtyCells: Int,
    list: List<TicTacToeItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(qtyCells),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Blue)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { item ->
            TicTacToeGridItem(
                ticTacToeItem = item
            )
        }
    }
}

@Composable
fun TicTacToeGridItem(
    ticTacToeItem: TicTacToeItem
) {

    val alphaAnim = remember { Animatable(0f) }
    var click by remember { mutableStateOf(false) }

    LaunchedEffect(click) {
        if (click) {
            alphaAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clickableWithoutAnimation {
                    click = true
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ticTacToeItem.label,
                fontSize = 36.sp,
                modifier = Modifier
                    .alpha(alphaAnim.value)
            )
        }
    }
}
