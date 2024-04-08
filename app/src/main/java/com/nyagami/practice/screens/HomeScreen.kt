package com.nyagami.practice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nyagami.practice.components.BookItem
import com.nyagami.practice.data.Book
import com.nyagami.practice.data.BookGenre
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (navController: NavController, allBooks: List<Book>) {
    var searchTerm by remember { mutableStateOf("") }
    Column (Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)){
        SearchBar(
            query = searchTerm,
            onQueryChange = {searchTerm = it},
            onSearch = {},
            active = false,
            onActiveChange = {},
            trailingIcon = {
                if(searchTerm.isNotEmpty()){
                    IconButton(onClick = { searchTerm = ""}) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            placeholder = {
                Text(text = "Tìm kiếm")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ){}
        LazyColumn(
            userScrollEnabled = true,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(items = allBooks.filter { it.name.lowercase().contains(searchTerm.lowercase()) }) {
                item -> BookItem(book = item, navController)
            }
        }
    }
}