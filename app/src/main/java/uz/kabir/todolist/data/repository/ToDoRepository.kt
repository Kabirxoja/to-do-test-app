package uz.kabir.todolist.data.repository

import kotlinx.coroutines.flow.Flow
import uz.kabir.todolist.data.local.ToDoEntity

interface ToDoRepository {
    fun getAllTodos(): Flow<List<ToDoEntity>>
    suspend fun insert(toDoEntity: ToDoEntity)
    suspend fun update(toDoEntity: ToDoEntity)
    suspend fun delete(toDoEntity: ToDoEntity)
    suspend fun getTodoById(id: Int): ToDoEntity?
}