// MainActivity.kt
package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Todo(
    val id: Int,
    val text: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                TodoApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TodoApp() {
    var todoText by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(0) }
    val todos = remember { mutableStateListOf<Todo>() }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    val completedCount = todos.count { it.isCompleted }
    val pendingCount = todos.size - completedCount

    var showAddTaskPanel by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "My Tasks",
                            fontSize = 28.sp,
//                            color=Color(0xFF6650a4),
                            color= MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        if (todos.isNotEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "$pendingCount pending",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                if (completedCount > 0) {
                                    Text(
                                        " • $completedCount completed",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddTaskPanel = true
                    scope.launch {
                        delay(100)
                        focusRequester.requestFocus()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            AnimatedVisibility(
                visible = showAddTaskPanel,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            "Add New Task",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = todoText,
                            onValueChange = { todoText = it },
                            placeholder = { Text("What needs to be done?") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                if (todoText.isNotBlank()) {
                                    todos.add(0, Todo(id = nextId++, text = todoText))
                                    todoText = ""
                                    focusManager.clearFocus()
                                    showAddTaskPanel = false
                                }
                            }),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    todoText = ""
                                    showAddTaskPanel = false
                                    focusManager.clearFocus()
                                }
                            ) {
                                Text("Cancel")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    if (todoText.isNotBlank()) {
                                        todos.add(0, Todo(id = nextId++, text = todoText))
                                        todoText = ""
                                        focusManager.clearFocus()
                                        showAddTaskPanel = false
                                    }
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Add Task")
                            }
                        }
                    }
                }
            }

            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "No tasks yet",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tap the + button to add a new task",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    val (activeTasks, completedTasks) = todos.partition { !it.isCompleted }

                    // Active tasks
                    itemsIndexed(activeTasks) { _, todo ->
                        val state = remember { MutableTransitionState(false).apply { targetState = true } }
                        AnimatedVisibility(
                            visibleState = state,
                            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)) +
                                    slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)),
                            exit = fadeOut()
                        ) {
                            TodoItem(
                                todo = todo,
                                onDelete = { todos.removeIf { it.id == todo.id } },
                                onToggleComplete = {
                                    val index = todos.indexOfFirst { it.id == todo.id }
                                    if (index != -1) {
                                        todos[index] = todos[index].copy(isCompleted = !todos[index].isCompleted)
                                    }
                                }
                            )
                        }
                    }

                    // Completed tasks header (only show if there are completed tasks)
                    if (completedTasks.isNotEmpty()) {
                        item {
                            Text(
                                "Completed",
                                modifier = Modifier.padding(vertical = 16.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        // Completed tasks
                        itemsIndexed(completedTasks) { _, todo ->
                            TodoItem(
                                todo = todo,
                                onDelete = { todos.removeIf { it.id == todo.id } },
                                onToggleComplete = {
                                    val index = todos.indexOfFirst { it.id == todo.id }
                                    if (index != -1) {
                                        todos[index] = todos[index].copy(isCompleted = !todos[index].isCompleted)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(todo: Todo, onDelete: () -> Unit, onToggleComplete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (todo.isCompleted) 0.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isCompleted)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onToggleComplete() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (todo.isCompleted) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "Mark as incomplete",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = todo.text,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (todo.isCompleted)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    else
                        MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.alpha(if (todo.isCompleted) 0.6f else 1f)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.75f)
                )
            }
        }
    }
}
