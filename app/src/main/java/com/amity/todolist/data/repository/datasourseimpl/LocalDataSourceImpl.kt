package com.amity.todolist.data.repository.datasourseimpl

import com.amity.todolist.data.local.TodoDAO
import com.amity.todolist.data.repository.datasource.LocalDataSource
import com.amity.todolist.domain.model.Todo
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor( private val todoDAO: TodoDAO) : LocalDataSource {
    override suspend fun getTodos(): List<Todo> {
        return todoDAO.getAllTodos()
    }

    override suspend fun saveTodos(todos: List<Todo>) {
        todoDAO.insertAllTodos(todos)
    }

    override suspend fun deleteTodos() {
        todoDAO.deleteAll()
    }
}