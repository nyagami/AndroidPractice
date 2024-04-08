package com.nyagami.practice.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.nyagami.practice.BottomScreen
import com.nyagami.practice.data.Book
import com.nyagami.practice.data.BookGenre
import java.util.Calendar

@Composable
fun CheckboxGroup(selecteds: List<Int>, onSelect: (index: Int) -> Unit, data: List<BookGenre>) {
    Column (Modifier.padding(vertical = 10.dp)) {
        Text(text = "Thể loại", fontWeight = FontWeight.Bold)
        data.forEachIndexed{
                index, it ->
            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = selecteds.contains(index), onCheckedChange = {onSelect(index)})
                Text(text = it.title)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetail(navController: NavHostController, book: Book?, addBook: ((book: Book) -> Unit)?, editBook: ((book: Book) -> Unit)?, deleteBook: ((book: Book) -> Unit)?) {
    val genres = listOf(BookGenre.PhePhan, BookGenre.SuThat, BookGenre.ChamBiem)
    var name by remember { mutableStateOf(book?.name ?: "") }
    var nameError by remember { mutableStateOf(false) }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var authorError by remember { mutableStateOf(false) }
    var isShowDatePicker by remember { mutableStateOf(false) }
    val releasedDate = rememberTimePickerState(
        initialHour = book?.hour ?: 0,
        initialMinute = book?.min ?: 0,
    )
    var selecteds by remember { mutableStateOf(book?.genres?.map { genres.indexOf(it) } ?: listOf(0)) }
    var genresError by remember { mutableStateOf(false) }
    Column (
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally),
    ){
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = it.isEmpty()
            },
            label = {
                Text(text = "Tên bài báo")
            },
            singleLine = true,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(text = if (nameError) "Vui lòng nhập tên" else "", color = MaterialTheme.colorScheme.error)
        OutlinedTextField(
            value = author,
            onValueChange = {
                author = it
                authorError = it.isEmpty()
            },
            label = {
                Text(text = "Tác giả")
            },
            singleLine = true,
        )
        Text(text = if (authorError) "Vui lòng nhập tác giả" else "", color = MaterialTheme.colorScheme.error)
        OutlinedTextField(
            label = {
                Text(text = "Giờ phát hành")
            },
            value = releasedDate.hour.toString().padStart(2, '0') + ":" + releasedDate.minute.toString().padStart(2, '0'),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isShowDatePicker = true }) {
                    Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = null)
                }
            },
            singleLine = true,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(vertical = 10.dp),
        )
        if(isShowDatePicker){
            TimePickerDialog(
                onCancel = { isShowDatePicker = false },
                onConfirm ={isShowDatePicker = false}
            ){
                TimePicker(state = releasedDate)
            }
        }
        CheckboxGroup(
            selecteds = selecteds,
            onSelect = { selecteds =
                if(!selecteds.contains(it)) selecteds + it else selecteds.filter { idx -> idx != it } },
            data = genres
        )
        Text(text = if (genresError) "Vui lòng chọn thể loại" else "", color = MaterialTheme.colorScheme.error)
        Row {
            Button(onClick = {
                if(name.isEmpty() || author.isEmpty() || selecteds.isEmpty()){
                    nameError = name.isEmpty()
                    authorError = author.isEmpty()
                    genresError = selecteds.isEmpty()
                }else{
                    if (addBook != null) {
                        addBook(Book(name, author, releasedDate.hour, releasedDate.minute, genres.filterIndexed { index, bookGenre -> selecteds.contains(index) }))
                    }
                    else if(editBook != null){
                        editBook(Book(name, author, releasedDate.hour, releasedDate.minute, genres.filterIndexed { index, bookGenre -> selecteds.contains(index) }))
                    }
                    navController.navigate(BottomScreen.Home.route)
                }
            }) {
                Text(text = if(book != null) "Sửa" else "Thêm", color = MaterialTheme.colorScheme.onPrimary)
            }
            if(book != null) {
                Button(
                    onClick = {
                        if (deleteBook != null) {
                            deleteBook(Book(name, author, 0, 0, genres))
                            navController.navigate(BottomScreen.Home.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "Xoá", color = MaterialTheme.colorScheme.onError)
                }
            }
        }

    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Huỷ") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}