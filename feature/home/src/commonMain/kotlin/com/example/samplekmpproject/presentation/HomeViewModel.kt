package com.example.samplekmpproject.presentation

import com.example.samplekmpproject.core.model.Todo
import com.example.samplekmpproject.data.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Home screen following MVVM. Keeps UI state and interacts with repository.
 * Placed under presentation package as requested.
 */
class HomeViewModel(
    private val repository: HomeRepository
) {
    data class UiState(
        val isLoading: Boolean = false,
        val todos: List<Todo> = emptyList(),
        val errorMessage: String? = null
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var loadJob: Job? = null

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    init {
        refresh()
    }

    fun refresh() {
        loadJob?.cancel()
        loadJob = scope.launch {
            repository.fetchTodos()
                .onStart { _uiState.update { it.copy(isLoading = true, errorMessage = null) } }
                .catch { t -> _uiState.update { it.copy(isLoading = false, errorMessage = t.message ?: t.toString()) } }
                .collect { data -> _uiState.update { it.copy(isLoading = false, todos = data, errorMessage = null) } }
        }
    }

    fun clear() {
        scope.cancel()
    }
}
