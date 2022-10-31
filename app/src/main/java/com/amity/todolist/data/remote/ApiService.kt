package com.amity.todolist.data.remote

import com.amity.todolist.data.remote.model.TodoDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(ApiConfig.GET_TODOS)
    suspend fun getTodos() : Response<ArrayList<TodoDTO>>
}