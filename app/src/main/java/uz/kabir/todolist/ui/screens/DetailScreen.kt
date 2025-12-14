package uz.kabir.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import uz.kabir.todolist.ui.viewmodel.DetailToDoViewModel
import androidx.compose.runtime.getValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    todoId: Int,
    onNavigateBack: () -> Unit,
    viewModel: DetailToDoViewModel = koinViewModel()
) {
    val todoItem by viewModel.todoItem.collectAsState()

    LaunchedEffect(todoId) {
        viewModel.fetchTodoById(todoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (todoItem == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading...")
            }
        } else {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedTime = sdf.format(Date(todoItem!!.createdTime))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Title: ${todoItem!!.title}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Description: ${todoItem!!.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Created At: $formattedTime",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
