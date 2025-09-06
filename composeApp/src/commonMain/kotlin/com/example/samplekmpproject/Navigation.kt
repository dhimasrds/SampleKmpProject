package com.example.samplekmpproject

sealed class Screen {
    data object Login : Screen()
    data class Home(val username: String) : Screen()
}