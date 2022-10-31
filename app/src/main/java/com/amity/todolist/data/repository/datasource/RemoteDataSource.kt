package com.amity.todolist.data.repository.datasource

import com.amity.todolist.data.remote.model.TodoDTO
import com.amity.todolist.domain.model.Todo
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getTodos() : Response<ArrayList<TodoDTO>>
}