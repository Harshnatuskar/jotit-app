package com.example.jotit.presentation

sealed class Screen(val route: String) {
    object VoidScreen : Screen("void_screen")
}