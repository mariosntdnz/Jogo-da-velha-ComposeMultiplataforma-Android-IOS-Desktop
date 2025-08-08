package org.example.project.presentation.ui.tictactoe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.example.project.getPlatform
import org.example.project.utils.clickableWithoutAnimation
import org.example.project.presentation.viewmodels.TicTacToeItem
import org.example.project.presentation.viewmodels.TicTacToeViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TicTacToeScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<TicTacToeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center
    ) {
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
                list = state.currentGridList,
                onClickItem = viewModel::play
            )

        }

        if (state.finishGame) {
            FinishGamePopup(
                finishGameText = state.finishGameText,
                visible = true,
                onDismiss = navController::navigateUp
            )
        }
    }

}

@Composable
fun TicTacToeGrid(
    qtyCells: Int,
    list: List<TicTacToeItem>,
    onClickItem: (Int, TicTacToeItem) -> Unit
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
        itemsIndexed(list) { index, item ->
            TicTacToeGridItem(
                ticTacToeItem = item,
                onClickItem = {
                    onClickItem(index, item)
                }
            )
        }
    }
}

@Composable
fun TicTacToeGridItem(
    ticTacToeItem: TicTacToeItem,
    onClickItem: () -> Unit
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
                    onClickItem()
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

@Composable
fun FinishGamePopup(
    finishGameText: String,
    visible: Boolean,
    backgroundColor: Color = Color(0xFF4CAF50),
    onDismiss: () -> Unit
) {
    val transition = updateTransition(targetState = visible, label = "popupTransition")

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 500)
        }, label = "alphaAnim"
    ) { if (it) 1f else 0f }

    val scale by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 500, easing = FastOutSlowInEasing)
        }, label = "scaleAnim"
    ) { if (it) 1f else 0.8f }

    if (alpha > 0f) {
        LaunchedEffect(visible) {
            if (visible) {
                delay(5000)
                onDismiss()
            }
        }
    }

    if (alpha > 0f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000))
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = finishGameText,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .scale(scale)
                    .alpha(alpha)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            )
        }
    }
}

