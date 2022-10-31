package com.amity.todolist.domain.usecase

import com.amity.todolist.domain.model.Todo
import com.amity.todolist.domain.repository.Repository
import com.amity.todolist.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotedUseCase @Inject constructor( val repository: Repository) {
    suspend fun execute() : Flow<Resource<List<Todo>>> =  repository.getTodos()

}