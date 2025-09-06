package com.example.samplekmpproject.core.network.service

import com.example.samplekmpproject.core.model.Todo
import com.example.samplekmpproject.core.network.ApiClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TodoService(private val api: ApiClient) {
    suspend fun getTodos(): List<Todo> = api.client.get("/todos").body()
    suspend fun getTodo(id: Int): Todo = api.client.get("/todos/$id").body()
}
