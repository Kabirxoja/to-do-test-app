package uz.kabir.todolist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import uz.kabir.todolist.data.repository.ToDoRepository
import uz.kabir.todolist.data.repository.ToDoRepositoryImpl
import uz.kabir.todolist.ui.viewmodel.AddToDoViewModel
import uz.kabir.todolist.ui.viewmodel.MainToDoViewModel
import org.koin.dsl.module
import uz.kabir.todolist.ui.viewmodel.DetailToDoViewModel

val appModule = module {

    // Repository
    single<ToDoRepository> {
        ToDoRepositoryImpl(todoDao = get())
    }

    // ViewModels
    viewModel {
        MainToDoViewModel(repository = get())
    }

    viewModel {
        AddToDoViewModel(repository = get())
    }

    viewModel {
        DetailToDoViewModel(repository = get())
    }
}