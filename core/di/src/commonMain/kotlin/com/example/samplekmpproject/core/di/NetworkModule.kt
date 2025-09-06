package com.example.samplekmpproject.core.di

import com.example.samplekmpproject.core.network.ApiClient
import com.example.samplekmpproject.core.network.HttpClientFactory
import org.koin.dsl.module
import com.example.samplekmpproject.config.BuildKonfig

val networkModule = module {
    single { HttpClientFactory().create() }
    single { ApiClient(baseUrl = BuildKonfig.BASE_URL, httpClient = get()) }
}
