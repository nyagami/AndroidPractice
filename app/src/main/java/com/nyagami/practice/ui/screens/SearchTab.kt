package com.nyagami.practice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nyagami.practice.ui.components.AppBar

object SearchTab: Tab {
    override val options: TabOptions
        @Composable
        get(){
            val title = "Tìm kiếm"
            val icon = rememberVectorPainter(Icons.Filled.Search)
            return TabOptions(index = 1u, title = title, icon = icon)
        }

    @Composable
    override fun Content() {
        Scaffold (
            topBar = {
                AppBar(title = "Search")
            }
        ){ paddingValues ->
            Column (modifier = Modifier.padding(paddingValues)) {
                Text(text = "Search tab")
            }
        }
    }
}