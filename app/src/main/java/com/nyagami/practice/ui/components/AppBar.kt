package com.nyagami.practice.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {
    val navigator = LocalNavigator.currentOrThrow
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (navigator.canPop) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )
}

