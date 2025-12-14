package uz.kabir.todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao{

    @Query("select * from todo_table order by id desc")
    fun getAllTodos(): Flow<List<ToDoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntity: ToDoEntity)

    @Update
    suspend fun update(toDoEntity: ToDoEntity)

    @Delete
    suspend fun delete(toDoEntity: ToDoEntity)

    @Query("SELECT * FROM todo_table WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): ToDoEntity?
}