package uz.kabir.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.kabir.todolist.data.local.ToDoEntity
import uz.kabir.todolist.data.repository.ToDoRepository

class DetailViewModel(private val repository: ToDoRepository) : ViewModel() {

    private val _todoItem = MutableStateFlow<ToDoEntity?>(null)
    val todoItem: StateFlow<ToDoEntity?> get() = _todoItem

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            _todoItem.value = repository.getTodoById(id)
        }
    }
}