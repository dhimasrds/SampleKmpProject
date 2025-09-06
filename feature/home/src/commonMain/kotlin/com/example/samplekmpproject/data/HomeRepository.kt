package com.example.samplekmpproject.data

import com.example.samplekmpproject.core.model.Todo
import com.example.samplekmpproject.core.network.safeCall
import com.example.samplekmpproject.core.di.AppProvider
import com.example.samplekmpproject.data.remote.TodoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Sample repository to demonstrate end-to-end Ktor request using Flow.
 * This will hit https://jsonplaceholder.typicode.com/todos and emit the list of todos.
 */
class HomeRepository(
    private val service: TodoService
) {
    fun fetchTodos(): Flow<List<Todo>> = flow {
        // We deliberately keep logging at the collector side to avoid duplicate logs here.
        val todos = safeCall { service.getTodos() }
        emit(todos)
    }.catch { throwable ->
        // Re-throw to let collector handle logging; alternatively, log here for debugging
        throw throwable
    }.flowOn(Dispatchers.Default)
}
