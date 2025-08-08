package org.example.project.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.project.utils.clickableWithoutAnimation

@Composable
fun DefaultEditCountComponent(
    modifier: Modifier = Modifier,
    currentNumber: String,
    errorMsg: String,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    LaunchedEffect(errorMsg) {
        isVisible = true
        delay(5000)
        isVisible = false
    }

    Column {
        Card(
            backgroundColor = Color.White.copy(alpha = .95f),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            EditCountComponent(
                modifier = modifier,
                onMinus = onMinus,
                onPlus = onPlus,
                minusContent = {
                    Text(
                        text = "-",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                plusContent = {
                    Text(
                        text = "+",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                countContent = {
                    Text(
                        text = currentNumber,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
        AnimatedVisibility(
            visible = isVisible && errorMsg.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMsg,
                fontSize = 12.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EditCountComponent(
    modifier: Modifier = Modifier,
    onPlus: () -> Unit,
    onMinus: () -> Unit,
    minusContent: @Composable () -> Unit,
    plusContent: @Composable () -> Unit,
    countContent: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clickableWithoutAnimation(onClick = onMinus)
        ) {
            minusContent()
        }
        Box {
            countContent()
        }
        Box(
            modifier = Modifier
                .clickableWithoutAnimation(onClick = onPlus)
        ) {
            plusContent()
        }
    }
}