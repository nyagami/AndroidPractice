package com.nyagami.practice.data

import androidx.annotation.DrawableRes
import java.util.Calendar

enum class BookGenre (val title: String){
    PhePhan("Phê phán"),
    SuThat("Sự thật"),
    ChamBiem("Châm biếm")
}
class Book (val name: String, val author: String, val hour: Int, val min: Int, val genres: List<BookGenre>){
}