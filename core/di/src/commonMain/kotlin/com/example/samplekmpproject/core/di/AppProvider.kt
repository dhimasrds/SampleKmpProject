package com.example.samplekmpproject.core.di

import com.example.samplekmpproject.core.network.ApiClient
import com.example.samplekmpproject.core.network.HttpClientFactory
import io.ktor.client.HttpClient

object AppProvider {
    // Singleton HttpClient shared across the app
    val httpClient: HttpClient by lazy {
        HttpClientFactory().create()
    }

    // Central ApiClient configuration
    val apiClient: ApiClient by lazy {
        ApiClient(
            baseUrl = "https://jsonplaceholder.typicode.com",
            httpClient = httpClient
        )
    }
}