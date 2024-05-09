package com.nyagami.practice.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nyagami.practice.data.Category
import com.nyagami.practice.db
import com.nyagami.practice.ui.components.AppBar
import com.nyagami.practice.ui.components.CategoryItem
import kotlinx.coroutines.launch

object CategoryTab: Tab {
    override val options: TabOptions
        @Composable
        get(){
            val title = "Tìm kiếm"
            val icon = rememberVectorPainter(Icons.Filled.Album)
            return TabOptions(index = 1u, title = title, icon = icon)
        }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val songDAO = db.songDao()
        var categories by remember {
            mutableStateOf(listOf<Category>())
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            categories = songDAO.getAllCategories()
        }
        Scaffold (
            topBar = {
                AppBar(title = "Category")
            }
        ){ paddingValues ->
            Column (modifier = Modifier.padding(paddingValues)) {
               categories.fastForEach {
                    CategoryItem(item = it)
               }
            }
        }
    }
}