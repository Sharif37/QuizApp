package com.example.quizapp

data class Question(
    val id: Int,
    val question: String,
    val optionOne: String?,
    val optionTwo: String?,
    val optionThree: String?,
    val optionFour: String?,
    val correctAns: Int,
    val countryInfo: CountryInfo
)


data class CountryInfo(
    val flag: String,
    val country: String,
    val code: String
)

