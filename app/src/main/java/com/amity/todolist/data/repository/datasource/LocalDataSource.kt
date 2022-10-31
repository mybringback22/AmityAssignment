package com.amity.todolist.data.repository.datasource

import com.amity.todolist.domain.model.Todo

interface LocalDataSource {
    suspend fun getTodos() : List<Todo>
    suspend fun saveTodos( todos : List<Todo>)
    suspend fun deleteTodos()
}