package com.nyagami.practice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.nyagami.practice.data.Song
import com.nyagami.practice.db
import com.nyagami.practice.ui.components.AppBar
import com.nyagami.practice.ui.components.Item
import kotlinx.coroutines.launch

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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val songDAO = db.songDao()
        var songs by remember {
            mutableStateOf(listOf<Song>())
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            songs = songDAO.getAll()
        }
        val navigator = LocalNavigator.currentOrThrow
        Scaffold (
            topBar = {
               AppBar(title = "List")
            }
        ) {paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                songs.forEach {
                    Item(item = it, onClick = {
                        if (it.id != null) {
                            navigator.parent?.push(DetailDataScreen(it.id))
                        }
                    })
                }
            }
        }
    }
}