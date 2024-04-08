package com.nyagami.practice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nyagami.practice.data.Book
import com.nyagami.practice.data.BookGenre
import com.nyagami.practice.screens.AddBookScreen
import com.nyagami.practice.screens.EditScreen
import com.nyagami.practice.screens.HomeScreen
import com.nyagami.practice.ui.theme.PracticeTheme
import java.util.Calendar

sealed class BottomScreen(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomScreen("home", Icons.Default.Home, "Tin tức")
    object AddBook : BottomScreen("add", Icons.Default.Add, "Thêm bài viết")

    object EditBook : BottomScreen("edit", Icons.Filled.Edit, "Sửa bài viết")
}

val items = listOf(
    BottomScreen.Home,
    BottomScreen.AddBook,
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer) },
                label = { Text(item.label) },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
}

@Composable
fun App(){
    val navController = rememberNavController()
    var allBooks by remember { mutableStateOf(((0..0).map {
        Book("Name $it", "Author", 0, 0, listOf(BookGenre.PhePhan, BookGenre.SuThat))
    })) }
    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = BottomScreen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(BottomScreen.Home.route) { HomeScreen(navController, allBooks) }
            composable(BottomScreen.AddBook.route) { AddBookScreen(navController, addBook = {
                allBooks = allBooks + it
            }) }
            composable(BottomScreen.EditBook.route + "/{bookName}"){
                allBooks.find { book -> book.name == it.arguments?.getString("bookName") }?.let { it1 ->
                    EditScreen(
                        navController,
                        it1,
                        editBook = {
                            allBooks = allBooks.map { book -> if (book.name == it.name) book else it }
                        },
                        deleteBook = {
                            allBooks = allBooks.filter { book -> book.name != it.name }
                        }
                    )
                }
            }
        }
    }
}