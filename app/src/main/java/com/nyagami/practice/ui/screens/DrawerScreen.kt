package com.nyagami.practice.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.nyagami.practice.data.Category

class DrawerScreen(category: Category): Screen {
    @Composable
    override fun Content() {
        Text(text = "Drawer screen")
    }
}