package com.amity.todolist.presentation.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amity.todolist.domain.model.Todo
import com.amity.todolist.domain.usecase.GetNotedUseCase
import com.amity.todolist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: GetNotedUseCase) : ViewModel() {

    fun getTodos() : LiveData<Resource<List<Todo>>> {
        return  flow{
            useCase.execute().collect{
                emit(it)
            }
        }.catch { e ->
            emit(Resource.Error(null,Exception(e).toString()))
        }.asLiveData(Dispatchers.IO)
    }

}