package com.amity.todolist.data.repository.datasourseimpl

import com.amity.todolist.data.remote.ApiService
import com.amity.todolist.data.remote.model.TodoDTO
import com.amity.todolist.data.repository.datasource.LocalDataSource
import com.amity.todolist.data.repository.datasource.RemoteDataSource
import com.amity.todolist.domain.model.Todo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor( private val apiService: ApiService): RemoteDataSource {

    override suspend fun getTodos(): Response<ArrayList<TodoDTO>> = apiService.getTodos()

}