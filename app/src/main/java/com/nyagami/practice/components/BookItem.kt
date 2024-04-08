package com.nyagami.practice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nyagami.practice.data.Book
import com.nyagami.practice.data.BookGenre
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun convertLongToTime(book: Book): String {
    return book.hour.toString().padStart(2, '0') + ":" + book.min.toString().padStart(2, '0')
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(book: Book, navController: NavController){
    Column (
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable (
                onClick = {
                    navController.navigate("edit/${book.name}")
                }
            )
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 8.dp, horizontal = 12.dp)

    ){
        Text(text = book.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Tác giả: ${book.author}")
            Text(text = convertLongToTime(book))
        }
        Text(text = "Thể loại: ${book.genres.map{it.title}.joinToString(", ")}")

    }
}