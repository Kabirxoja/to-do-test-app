package uz.kabir.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.kabir.todolist.data.local.ToDoEntity
import uz.kabir.todolist.data.repository.ToDoRepository

class AddToDoViewModel(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()


    fun onTitleChange(value: String) {
        _title.value = value
    }

    fun onDescriptionChange(value: String) {
        _description.value = value
    }

    fun addTodo(onSuccess: () -> Unit) {
        if (_title.value.isNotBlank() && _description.value.isNotBlank()) {
            viewModelScope.launch {
                repository.insert(
                    ToDoEntity(
                        title = _title.value.trim(),
                        description = _description.value.trim(),
                        createdTime = System.currentTimeMillis(),
                        isCompleted = false
                    )
                )
                _title.value = ""
                _description.value = ""
                onSuccess()
            }
        }
    }
}