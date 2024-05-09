package com.nyagami.practice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.nyagami.practice.ui.components.AppBar

class DetailDataScreen(private val songId: Long): Screen {
    @Composable
    override fun Content() {
        Scaffold (
            topBar = {
                AppBar(title = "Detail")
            }
        ) {paddingValues ->
            Column (Modifier.padding(paddingValues)) {
                
            }
        }
    }
}