package com.amity.todolist.data.repository

import android.app.Application
import com.amity.todolist.R
import com.amity.todolist.data.repository.datasource.LocalDataSource
import com.amity.todolist.data.repository.datasource.RemoteDataSource
import com.amity.todolist.domain.model.Todo
import com.amity.todolist.domain.repository.Repository
import com.amity.todolist.utils.ConnectivityObserver
import com.amity.todolist.utils.Resource
import com.amity.todolist.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val context : Application,
    private val connectivityObserver: ConnectivityObserver,
) : Repository {
    override suspend fun getTodos(): Flow<Resource<List<Todo>>> {
        return flow {
            if (!Utils.isNetworkAvailable(context.applicationContext)) {
                emit(getTodosFromDb(null))
            }
            connectivityObserver.observe()
                .collect { networkStatus ->
                    when (networkStatus) {
                        is ConnectivityObserver.Status.Available -> {
                            emit(Resource.Loading())
                            try {
                                val response = remoteDataSource.getTodos()
                                if (response.isSuccessful) {
                                    val todos = response.body()?.map {
                                        Todo(it.id , it.title , it.completed)
                                    }
                                    todos?.let {
                                        localDataSource.deleteTodos()
                                        localDataSource.saveTodos(it)
                                    }
                                    emit(getTodosFromDb(null))
                                } else {
                                    emit(getTodosFromDb(context.getString(R.string.server_error)))
                                }
                            } catch (e: Exception) {
                                emit(getTodosFromDb(context.getString(R.string.server_error)))
                            }
                        }
                        is ConnectivityObserver.Status.Unavailable -> {
                        }
                    }
                }
        }
    }

    private suspend fun getTodosFromDb(errorMessage : String?): Resource<List<Todo>> {
        val dbArticles = localDataSource.getTodos()
        if(dbArticles.isEmpty()){
            errorMessage?.let {
                return Resource.Error(null , errorMessage)
            }?: kotlin.run {
                return Resource.Error(null , context.getString(R.string.no_data_msg))
            }
        }else{
            errorMessage?.let {
                return Resource.Error(dbArticles , errorMessage)
            }?: kotlin.run {
                return Resource.Success(dbArticles)
            }
        }
    }

}