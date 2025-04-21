//// components/InputSection.kt
//package com.example.myfirstapp.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.Alignment
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InputSection(
//    onAddTodo: (String) -> Unit
//) {
//    var text by remember { mutableStateOf("") }
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        TextField(
//            value = text,
//            onValueChange = { text = it },
//            placeholder = { Text("Enter a todo") },
//            modifier = Modifier.weight(1f),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            keyboardActions = KeyboardActions(onDone = {
//                if (text.isNotBlank()) {
//                    onAddTodo(text)
//                    text = ""
//                }
//            })
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Button(onClick = {
//            if (text.isNotBlank()) {
//                onAddTodo(text)
//                text = ""
//            }
//        }) {
//            Text("Add")
//        }
//    }
//}
