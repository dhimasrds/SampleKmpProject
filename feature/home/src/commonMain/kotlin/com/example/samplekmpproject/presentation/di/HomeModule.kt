package com.example.samplekmpproject.presentation.di

import com.example.samplekmpproject.data.HomeRepository
import com.example.samplekmpproject.data.remote.TodoService
import com.example.samplekmpproject.presentation.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory { TodoService(get()) }
    factory { HomeRepository(get()) }
    factory { HomeViewModel(get()) }
}
