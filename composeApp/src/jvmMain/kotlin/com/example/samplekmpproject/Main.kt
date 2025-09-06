package com.example.samplekmpproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import com.example.samplekmpproject.core.di.networkModule
import com.example.samplekmpproject.presentation.di.homeModule

fun main() = application {
    // Ensure Koin is started once for Desktop/JVM
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            printLogger()
            modules(networkModule, homeModule)
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "SampleKmpProject",
    ) {
        App()
    }
}