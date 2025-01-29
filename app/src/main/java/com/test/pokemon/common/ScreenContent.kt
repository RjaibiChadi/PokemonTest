package com.test.pokemon.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    toolbarTitle: String,
    leftToolbarIcon : ImageVector = Icons.Default.ArrowBack,
    leftToolbarClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = toolbarTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    if (leftToolbarClick != null)
                        IconButton(
                            modifier = modifier,
                            onClick = leftToolbarClick,
                        ) {
                            Icon(
                                imageVector = leftToolbarIcon,
                                contentDescription = null,
                            )
                        }
                },

            )
        },
        content = { padding ->
            Box(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                content()
            }
        }
    )
}