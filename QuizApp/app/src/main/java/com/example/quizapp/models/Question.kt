package com.example.quizapp.models

data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThere: String,
    val optionFour: String,
    val correctAnswer: Int
)
