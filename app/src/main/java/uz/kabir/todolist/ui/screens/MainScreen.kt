package uz.kabir.todolist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import uz.kabir.todolist.data.local.ToDoEntity
import uz.kabir.todolist.ui.viewmodel.MainToDoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: MainToDoViewModel = koinViewModel()
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task List Screen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task Icon"
                )
            }
        }
    ) { paddingValues ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tasks yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                items(
                    items = items,
                    key = { it.id }
                ) { todoItem ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { dismissValue ->
                            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.deleteTodo(todoItem)
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = "Delete",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        enableDismissFromStartToEnd = false
                    ) {
                        TodoItemCard(
                            todoItem = todoItem,
                            onCheckedChange = { viewModel.toggleCompleted(todoItem) },
                            onItemClick = { onNavigateToDetail(todoItem.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemCard(
    todoItem: ToDoEntity,
    onCheckedChange: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedTime = sdf.format(Date(todoItem.createdTime))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todoItem.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (todoItem.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (todoItem.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            Checkbox(
                checked = todoItem.isCompleted,
                onCheckedChange = onCheckedChange
            )
        }
    }
}
