package org.example.project.presentation.ui.tictactoe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.example.project.domain.models.TicTacToeItem
import org.example.project.presentation.ui.components.BackButton
import org.example.project.presentation.ui.components.SimpleTextCard
import org.example.project.presentation.viewmodels.TicTacToeViewModel
import org.example.project.utils.clickableWithoutAnimation
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun TicTacToeScreen(
    navController: NavController,
    gameId: Long,
    gridLength: Int,
    isOnlineGame: Boolean
) {

    val viewModel = koinViewModel<TicTacToeViewModel>(
        parameters = {
            parametersOf(gameId, gridLength, isOnlineGame)
        }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    val infiniteTransition = rememberInfiniteTransition()
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val animatedBackgroundColor = lerp(
        start = Color.Blue.copy(alpha = .25f),
        stop = Color.Blue.copy(alpha = .7f),
        fraction = animatedAlpha
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentPlayerLabel(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                backgroundColor = animatedBackgroundColor,
                namePlayer = state.currentPlayer.name,
                markerPlayer = state.currentPlayer.marker
            )
            TicTacToeGrid(
                qtyCells = state.gridLength,
                list = state.currentGridList,
                onClickItem = viewModel::makeAMove
            )
        }

        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp)
            ,
            onClick = navController::navigateUp
        )

        if (state.gameStateType.isFinished()) {
            FinishGamePopup(
                finishGameText = state.endedGameText,
                visible = true,
                onDismiss = {
                    viewModel.onFinishGame {
                        navController.navigateUp()
                    }
                }
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
    BoxWithConstraints(
        modifier = Modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val size = minOf(maxWidth, maxHeight)
        LazyVerticalGrid(
            columns = GridCells.Fixed(qtyCells),
            modifier = Modifier
                .size(size)
                .background(Color.Blue)
            ,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
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
}

@Composable
fun TicTacToeGridItem(
    ticTacToeItem: TicTacToeItem,
    onClickItem: () -> Unit
) {

    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(ticTacToeItem.label) {
        if (ticTacToeItem.label.isNotEmpty()) {
            alphaAnim.snapTo(0f)
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clickableWithoutAnimation {
                    onClickItem()
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ticTacToeItem.label,
                fontSize = (minWidth.value * 0.5f).sp,
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

@Composable
fun CurrentPlayerLabel(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue.copy(alpha = .7f),
    namePlayer: String,
    markerPlayer: String
) {
    SimpleTextCard(
        modifier = modifier,
        text = "$namePlayer  ->  $markerPlayer",
        backgroundColor = backgroundColor,
        textColor = Color.Black
    )
}

