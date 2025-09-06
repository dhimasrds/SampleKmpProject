package com.example.samplekmpproject.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.client.request.accept
import io.ktor.client.request.header

class ApiClient(
    baseUrl: String,
    private val tokenProvider: () -> String? = { null },
    httpClient: HttpClient,
) {
    val client: HttpClient = httpClient.config {
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 30_000
        }
        defaultRequest {
            url(baseUrl)
            header(HttpHeaders.Accept, ContentType.Application.Json.toString())
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            tokenProvider()?.let { header(HttpHeaders.Authorization, "Bearer $it") }
        }
    }
}
