//// components/TodoList.kt
//package com.example.myfirstapp.components
//
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.runtime.Composable
//import com.example.myfirstapp.Todo
//
//@Composable
//fun TodoList(
//    todos: List<Todo>,
//    onDelete: (Todo) -> Unit
//) {
//    LazyColumn {
//        items(todos) { todo ->
//            TodoItem(todo = todo, onDelete = { onDelete(todo) })
//        }
//    }
//}
