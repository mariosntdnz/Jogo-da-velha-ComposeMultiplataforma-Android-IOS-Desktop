package org.example.project.presentation.ui.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.example.project.presentation.navigation.Screen
import org.example.project.presentation.ui.components.DefaultEditCountComponent
import org.example.project.presentation.viewmodels.StartGameViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartGameScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<StartGameViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = .25f))
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tic Tac Toe",
            fontSize = 32.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(.75f))
        Text(
            text = "Escolha o tamanho do tabuleiro !",
            fontSize = 16.sp,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultEditCountComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
            ,
            currentNumber = state.gridLength.toString(),
            errorMsg = state.errorMsg,
            onPlus = viewModel::onPlusLength,
            onMinus = viewModel::onMinusLength
        )
        Spacer(modifier = Modifier.weight(.5f))
        TextField(
            value = state.firstPlayerName,
            onValueChange = viewModel::onChangeFirstPlayerName,
            shape = RoundedCornerShape(16.dp),
            label = { Text("Nome do Player 1") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.secondPlayerName,
            onValueChange = viewModel::onChangeSecondPlayerName,
            shape = RoundedCornerShape(16.dp),
            label = { Text("Nome do Player 2") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(.5f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
            ,
            shape = RoundedCornerShape(100),
            onClick = {
                navController.navigate(
                    Screen.TicTacToe(
                        gridLength = state.gridLength,
                        firstPlayerName = state.firstPlayerNameOrDefault,
                        secondPlayerName = state.secondPlayerNameOrDefault
                    )
                )
            }
        ) {
            Text(
                text = "start",
                fontSize = 24.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}