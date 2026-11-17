package com.example.rickmortyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.rickmortyapp.ui.theme.RickMortyAppTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
sealed interface TaskAppNavKey : NavKey

@Serializable
data object Tasks : TaskAppNavKey
@Serializable
data class TaskDetail(val id: String) : TaskAppNavKey

@Composable
fun rememberTaskAppNavBackStack(vararg elements: TaskAppNavKey): NavBackStack<TaskAppNavKey> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyAppTheme {
                val backStack = rememberTaskAppNavBackStack(Tasks)

                NavDisplay(
                    backStack = backStack,
                    onBack = {backStack.removeLastOrNull()},
                    entryDecorators = listOf(
                        // Add the default decorators for managing scenes and saving state
                        rememberSaveableStateHolderNavEntryDecorator(),
                        // Then add the view model store decorator
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<Tasks> {
                            val viewModel = viewModel<TaskListViewModel>()
                            val tasks by viewModel.tasks.collectAsStateWithLifecycle()

                            TaskListScreen(
                            onNavigateToDetail = {id ->
                                backStack.add(TaskDetail(id))
                            }
                        ) }
                        entry<TaskDetail> {
                            val viewModel = viewModel<TaskListViewModel>()
                            val tasks by viewModel.tasks.collectAsStateWithLifecycle()
                            TaskDetailScreen(
                                id = it.id
                        ) }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskListScreen(
    onNavigateToDetail: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column() {
            Text(text = "Task LIST")
            Button(onClick = {onNavigateToDetail("12")}) {
                Text(text = "Add Task")
            }
        }
    }
}

@Composable
fun TaskDetailScreen(id: String) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        Text(text = "Task DETAIL$id")
    }
}