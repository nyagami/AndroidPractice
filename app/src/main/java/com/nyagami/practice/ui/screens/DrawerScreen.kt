package com.nyagami.practice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.core.screen.Screen
import com.nyagami.practice.data.Category
import com.nyagami.practice.data.Group
import com.nyagami.practice.db
import com.nyagami.practice.ui.components.AppBar
import kotlinx.coroutines.launch

class DrawerScreen(category: Category): Screen {
    val songDAO = db.songDao()
    val category = category
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        var genres by remember {
            mutableStateOf(listOf<Group>())
        }
        val coroutineScope = rememberCoroutineScope()
        coroutineScope.launch {
            genres = songDAO.getGenresByCategory(album = category.album)
        }
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Genres", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                    Divider()
                    genres.fastForEachIndexed {index, group ->
                        NavigationDrawerItem(
                            label = { Text(text = group.genre) },
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index}
                        )
                    }
                }
            },
        ) {
            Scaffold (
                topBar = {
                    AppBar(title = "Drawer")
                }
            ) { paddingValues ->  
                Column (modifier = Modifier.padding(paddingValues)) {
                    
                }
            }
        }
    }
}