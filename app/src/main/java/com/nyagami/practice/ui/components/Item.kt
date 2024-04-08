package com.nyagami.practice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nyagami.practice.data.Song

@Composable
fun Item(item: Song, onClick: () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    Column (modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .padding(4.dp)
        .clickable(onClick = onClick)) {
        Text(text = item.name, color = MaterialTheme.colorScheme.onPrimaryContainer)
        Text(text = "Tác giả: ${item.artist}")
    }
}