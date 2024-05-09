package com.nyagami.practice.ui.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

object HomeScreen: Screen {
    private val tabs = listOf(
        ListTab,
        SearchTab
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        TabNavigator (
            tab = ListTab
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        tabs.fastForEach {
                            NavigationBarItem(it)
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigator?.push(WriteDataScreen(null)) },
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                }
            ){
                CurrentTab()
            }
        }
    }
}

@Composable
private fun RowScope.NavigationBarItem(tab: Tab){
    val tabNavigator = LocalTabNavigator.current
    val selected = tabNavigator.current == tab
    NavigationBarItem(
        selected = selected,
        onClick = { if(!selected){ tabNavigator.current = tab } },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } }
    )
}