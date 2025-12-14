package uz.kabir.todolist.data.repository

import kotlinx.coroutines.flow.Flow
import uz.kabir.todolist.data.local.ToDoDao
import uz.kabir.todolist.data.local.ToDoEntity

class ToDoRepositoryImpl(
    private val todoDao: ToDoDao
) : ToDoRepository {
    override fun getAllTodos(): Flow<List<ToDoEntity>> {
        return todoDao.getAllTodos()
    }

    override suspend fun insert(toDoEntity: ToDoEntity) {
        todoDao.insert(toDoEntity)
    }

    override suspend fun update(toDoEntity: ToDoEntity) {
        todoDao.update(toDoEntity)
    }

    override suspend fun delete(toDoEntity: ToDoEntity) {
        todoDao.delete(toDoEntity)
    }

    override suspend fun getTodoById(id: Int): ToDoEntity? {
        return todoDao.getTodoById(id)
    }

}