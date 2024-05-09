package com.nyagami.practice.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun parseDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(
    label: String,
    state: DatePickerState,
) {
    var showPicker by remember {
        mutableStateOf(false)
    }
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = label)
        OutlinedButton(onClick = { showPicker = !showPicker }, Modifier.padding(start = 4.dp)) {
            Text(text = if (state.selectedDateMillis != null) parseDate(state.selectedDateMillis!!) else "DD/MM/YYYY")
        }
    }
    if(showPicker){
        DatePickerDialog(
            onDismissRequest = { showPicker = false},
            confirmButton = { Button(onClick = {showPicker = false}) {
                Text(text = "Ok")
            }},
        ) {
            DatePicker(state = state)
        }
    }
}