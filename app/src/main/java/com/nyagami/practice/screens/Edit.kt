package com.nyagami.practice.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.nyagami.practice.components.BookDetail
import com.nyagami.practice.data.Book
import com.nyagami.practice.data.BookGenre

@Composable
fun EditScreen(navHostController: NavHostController, book: Book, editBook: (book: Book) -> Unit, deleteBook: (book: Book) -> Unit){
    BookDetail(navHostController, book, null, editBook, deleteBook)
}