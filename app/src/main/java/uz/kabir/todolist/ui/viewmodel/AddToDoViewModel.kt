package uz.kabir.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.kabir.todolist.data.local.ToDoEntity
import uz.kabir.todolist.data.repository.ToDoRepository

class AddToDoViewModel(
    private val repository: ToDoRepository
) : ViewModel() {

    val title = MutableStateFlow("")
    val description = MutableStateFlow("")

    val isFormValid: StateFlow<Boolean> = combine(title, description) { t, d ->
        t.isNotBlank() && d.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun addTodo(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (title.value.isNotBlank() && description.value.isNotBlank()) {
                repository.insert(
                    ToDoEntity(
                        title = title.value.trim(),
                        description = description.value.trim(),
                        createdTime = System.currentTimeMillis(),
                        isCompleted = false
                    )
                )
                title.value = ""
                description.value = ""
                onSuccess()
            }
        }
    }
}
