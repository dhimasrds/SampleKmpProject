package com.example.samplekmpproject

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import com.example.samplekmpproject.core.di.networkModule
import com.example.samplekmpproject.presentation.di.homeModule

fun MainViewController() = ComposeUIViewController {
    // Ensure Koin is started for iOS only once per UI lifetime
    remember(Unit) {
        runCatching {
            startKoin {
                printLogger()
                modules(networkModule, homeModule)
            }
        }
    }
    App()
}