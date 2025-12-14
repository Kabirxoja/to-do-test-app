package uz.kabir.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.kabir.todolist.data.local.ToDoEntity
import uz.kabir.todolist.data.repository.ToDoRepository

class MainToDoViewModel(
    private val repository: ToDoRepository
) : ViewModel() {

    val items: StateFlow<List<ToDoEntity>> = repository.getAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun toggleCompleted(toDoEntity: ToDoEntity) {
        viewModelScope.launch {
            repository.update(
                toDoEntity.copy(isCompleted = !toDoEntity.isCompleted)
            )
        }
    }

    fun deleteTodo(toDoEntity: ToDoEntity) {
        viewModelScope.launch {
            repository.delete(toDoEntity)
        }
    }
}