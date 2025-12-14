package uz.kabir.todolist.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.kabir.todolist.ui.viewmodel.AddToDoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddToDoViewModel = koinViewModel()
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Task Screen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back icon"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Task title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(16.dp)
            )


            OutlinedTextField(
                value = description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Task description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                maxLines = 4,
                shape = RoundedCornerShape(16.dp)
            )

            Button(
                onClick = { viewModel.addTodo(onSuccess = onNavigateBack) },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text("Add Task")
            }
        }
    }
}