package org.example.project.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutAnimation(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) = composed {
    this.clickable(
        onClick = onClick,
        interactionSource = MutableInteractionSource(),
        indication = null,
        role = null,
        enabled = enabled
    )
}