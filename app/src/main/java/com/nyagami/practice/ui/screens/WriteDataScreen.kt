package com.nyagami.practice.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nyagami.practice.data.Song
import com.nyagami.practice.db
import com.nyagami.practice.ui.components.AppBar
import com.nyagami.practice.ui.components.DateInput
import com.nyagami.practice.ui.components.SelectPicker
import com.nyagami.practice.ui.components.TimeInput
import kotlinx.coroutines.launch

class WriteDataScreen(song: Song?): Screen {
    private val albums = listOf(
        "Cho 1 tình yêu",
        "Nỗi yêu bé dại",
        "Cây lặng - gió ngừng",
        "Có dừng được không",
        "Đây là mơ",
        "Ở giữa cuộc đời"
    )
    private val genres = listOf(
        "country",
        "blues",
        "rock",
        "pop"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val songDAO = db.songDao();
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        var name by remember { mutableStateOf("") }
        var artist by remember { mutableStateOf("") }
        var albumIndex by remember { mutableIntStateOf(0) }
        var genreIndex by remember { mutableIntStateOf(0) }
        var liked by remember { mutableStateOf(false) }
        val context = LocalContext.current
        var dateState = rememberDatePickerState()
        var timeState = rememberTimePickerState()
        Scaffold (
            topBar = {
                AppBar(title = "Thêm bài hát")
            }
        ){paddingValues ->
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
            )
            {
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = {
                        Text(text = "Tên")
                    }
                )
                OutlinedTextField(
                    value = artist,
                    onValueChange = {artist = it},
                    label = {
                        Text(text = "Tác giả")
                    }
                )
                SelectPicker(label = "Album", labels = albums, selectedIndex = albumIndex, onSelect = {albumIndex = it})
                SelectPicker(label = "Genre", labels = genres, selectedIndex = genreIndex, onSelect = {genreIndex = it})
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Liked", textAlign = TextAlign.Center)
                    Checkbox(checked = liked, onCheckedChange = {liked = it})
                }
                DateInput(label = "Date", state = dateState)
                TimeInput(label = "Time", state = timeState)
                Button(onClick = {
                    coroutineScope.launch {
                        if (name.isBlank() || artist.isBlank()){
                            Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                        }else{
                            songDAO.insertAll(Song(null, name, artist, albums[albumIndex], genres[genreIndex], liked))
                            navigator.pop()
                        }
                    }
                }, modifier = Modifier.width(100.dp)) {
                    Text(text = "Tạo")
                }
            }
        }
    }
}
