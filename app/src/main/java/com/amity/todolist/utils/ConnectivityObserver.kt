package com.amity.todolist.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    sealed class Status(status : String){
        class  Available(status : String) : Status(status)
        class  Unavailable(status : String) : Status(status)
    }

}