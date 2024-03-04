package com.nyagami.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.nyagami.practice.data.Book
import com.nyagami.practice.ui.theme.PracticeTheme
import kotlinx.coroutines.launch


val all_books = listOf<Book>(
    Book(R.drawable.cover_1, "Book 1", "Author 1", 10),
    Book(R.drawable.cover_2, "Book 2", "Author 2", 12),
    Book(R.drawable.cover_3, "Book 3", "Author 3", 13),
    Book(R.drawable.cover_4, "Book 4", "Author 4", 14),
    Book(R.drawable.cover_1, "Book 5", "Author 1", 10),
    Book(R.drawable.cover_2, "Book 6", "Author 2", 12),
    Book(R.drawable.cover_3, "Book 7", "Author 3", 13),
    Book(R.drawable.cover_4, "Book 8", "Author 4", 14)
)

val fav_books = listOf<Book>(
    Book(R.drawable.cover_1, "Book 1", "Author 1", 10),
    Book(R.drawable.cover_2, "Book 2", "Author 2", 12),
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
                    TabLayout()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    // on below line we are creating variable for pager state.
    val pagerState = rememberPagerState(pageCount = 3)
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        // on below line we are calling tabs
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

// on below line we are
// creating a function for tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "All books",
        "Your book favourite",
        "Search"
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        list[index],
                        // on below line we are specifying the text color
                        // for the text in that tab
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

// on below line we are creating a tab content method
// in which we will be displaying the individual page of our tab .
@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState, modifier = Modifier
        .fillMaxSize()
        .padding(4.dp), verticalAlignment = Alignment.Top) {
            page ->
        when (page) {
            0 -> AllBook()
            1 -> FavBook()
            2 -> Search()
        }
    }
}

@Composable
fun AllBook(){
    BookList(books = all_books)
}

@Composable
fun FavBook() {
    BookList(books = fav_books)
}

@Composable
fun Search(){
    var searchKey by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = searchKey,
            onValueChange = {searchKey = it},
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            modifier = Modifier.padding(0.dp, 4.dp)
        )
        BookList(books = all_books.filter { it.name.lowercase().contains(searchKey.lowercase()) })
    }
}

@Composable
fun BookList (books: List<Book>){
    Column(
        modifier = Modifier
            .verticalScroll(
                rememberScrollState()
            )
            .fillMaxSize()
    ) {
        books.forEach {
            book ->  BookItem(book = book)
        }
    }
}

@Composable
fun BookItem (book: Book) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(4.dp),
    ) {
        Image(painter = painterResource(id = book.cover), contentDescription = null)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 5.dp)
        ) {
            Text(text = book.name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Row (){
                Text(text = "Author: ")
                Text(text = book.author)
            }
            Row (){
                Text(text = "Likes: ")
                Text(text = book.likes.toString())
            }
        }
    }
}