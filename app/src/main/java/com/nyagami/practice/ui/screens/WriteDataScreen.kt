package com.nyagami.practice.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.nyagami.practice.data.SongDbHelper

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
        val navigator = LocalNavigator.currentOrThrow
        val dbHelper = SongDbHelper(LocalContext.current)
        var name by remember { mutableStateOf("") }
        var artist by remember { mutableStateOf("") }
        var album by remember { mutableStateOf(albums[0]) }
        var genre by remember { mutableStateOf(genres[0]) }
        var liked by remember { mutableStateOf(false) }
        val context = LocalContext.current
        Scaffold (
            topBar = {
                IconButton(onClick = { navigator.pop()}) {
                    Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
                }
                Box (
                    Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .padding(top = 12.dp)) {
                    Text(text = "Thêm bài hát")
                }
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
                DropDownInput("Album", options = albums, value = album, onSelect = {album = it})
                DropDownInput("Genre", options = genres, value = genre, onSelect = {genre = it})
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Liked", textAlign = TextAlign.Center)
                    Checkbox(checked = liked, onCheckedChange = {liked = it})
                }
                Button(onClick = {
                    if (name.isBlank() || artist.isBlank()){
                        Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    }else{
                        dbHelper.addSong(Song(1, name, artist, album, genre, liked))
                    }
                }, modifier = Modifier.width(100.dp)) {
                    Text(text = "Tạo")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownInput(label: String, options: List<String>, value: String, onSelect: (option: String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = value,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onSelect(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}