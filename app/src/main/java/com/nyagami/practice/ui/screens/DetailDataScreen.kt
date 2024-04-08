package com.nyagami.practice.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class DetailDataScreen(private val songId: Long): Screen {
    @Composable
    override fun Content() {
        Text(text = "Content")
    }
}