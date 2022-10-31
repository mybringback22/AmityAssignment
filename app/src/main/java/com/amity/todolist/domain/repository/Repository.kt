package com.amity.todolist.domain.repository

import com.amity.todolist.domain.model.Todo
import com.amity.todolist.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getTodos(): Flow<Resource<List<Todo>>>
}