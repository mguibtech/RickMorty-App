package com.example.rickmortyapp

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskListViewModel : ViewModel(){

    private val _tasks = MutableStateFlow(listOf(String))
    val tasks = _tasks.asStateFlow()

    init {
        Log.d("TaskListViewmodel", "msg")
    }
}