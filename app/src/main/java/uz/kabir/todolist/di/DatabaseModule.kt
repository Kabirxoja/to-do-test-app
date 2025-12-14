package uz.kabir.todolist.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.kabir.todolist.data.local.ToDoDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            "todo_database"
        ).build()
    }

    single {
        get<ToDoDatabase>().toDoDao()
    }
}