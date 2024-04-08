package com.nyagami.practice.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.nyagami.practice.components.BookDetail
import com.nyagami.practice.data.Book

@Composable
fun AddBookScreen(navController: NavHostController, addBook: (book: Book) -> Unit) {
    BookDetail(navController,null, addBook, null, null)
}