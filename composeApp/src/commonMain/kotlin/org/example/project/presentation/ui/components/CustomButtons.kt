package org.example.project.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun SimpleButton(
    modifierButton: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onClick: () -> Unit
) {
    Button(
        modifier = modifierButton,
        shape = RoundedCornerShape(100),
        onClick = onClick
    ) {
        Text(
            modifier = modifierText,
            text = text,
            fontSize = fontSize,
            color = Color.Red,
            lineHeight = lineHeight,
            textAlign = TextAlign.Center
        )
    }
}