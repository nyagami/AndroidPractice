package com.nyagami.practice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nyagami.practice.data.Category
import com.nyagami.practice.ui.screens.DrawerScreen

@Composable
fun CategoryItem(item: Category) {
    val navigator = LocalNavigator.currentOrThrow
    Row (
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .clickable(onClick = {
                navigator.parent?.push(DrawerScreen(item))
            }),
    ){
        Icon(imageVector = Icons.Filled.Label, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        Column (
            modifier = Modifier.padding(start = 6.dp)
        ) {
            Text(
                text = item.album,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Số lượng: ${item.numberOfSongs}")
        }
    }
}