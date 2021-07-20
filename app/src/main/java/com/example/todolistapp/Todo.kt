package com.example.todolistapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    var title: String,
    var description: String,
    var date: Long,
    var time: Long,
    var category: String,
    var isFinished: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
){}