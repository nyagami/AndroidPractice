package com.nyagami.practice.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nyagami.practice.data.Song
import com.nyagami.practice.ui.components.Item

object ListTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Danh sách"
            val icon = rememberVectorPainter(Icons.Filled.FormatListNumbered)
            return TabOptions(
                index = 0u,
                title = title,
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold {paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Item(
                    item = Song(1, "a", "b", "c", "as", true),
                    onClick = {navigator.parent?.push(DetailDataScreen(1))}
                )
            }
        }
    }
}