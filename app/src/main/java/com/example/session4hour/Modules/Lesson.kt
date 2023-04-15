package com.example.session4hour.Modules

data class Lesson(
    val id : Int,
    val idCourse: Int,
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val datetime:String,
    val duration: Int
)
