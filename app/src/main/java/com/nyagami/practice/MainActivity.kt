package com.nyagami.practice
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import cafe.adriel.voyager.navigator.Navigator
import com.nyagami.practice.data.SongDb
import com.nyagami.practice.ui.screens.HomeScreen
import com.nyagami.practice.ui.theme.PracticeTheme
lateinit var db: SongDb;
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, SongDb::class.java, "song").build()
        setContent {
            PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator (screen = HomeScreen)
                }
            }
        }
    }
}
