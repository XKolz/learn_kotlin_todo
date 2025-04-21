//// components/TodoItem.kt
//package com.example.myfirstapp.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.myfirstapp.Todo
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TodoItem(todo: Todo, onDelete: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(todo.text, modifier = Modifier.weight(1f))
//            IconButton(onClick = onDelete) {
//                Icon(Icons.Default.Delete, contentDescription = "Delete")
//            }
//        }
//    }
//}
