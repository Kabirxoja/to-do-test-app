package uz.kabir.todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val description:String,
    val createdTime:Long,
    val isCompleted: Boolean = false
)