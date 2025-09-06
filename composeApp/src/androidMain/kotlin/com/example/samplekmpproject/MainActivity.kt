package com.example.samplekmpproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import com.example.samplekmpproject.core.di.networkModule
import com.example.samplekmpproject.presentation.di.homeModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize Koin once on Android
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                printLogger()
                modules(networkModule, homeModule)
            }
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}