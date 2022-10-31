package com.amity.todolist.utils

sealed class Resource <out T>{
    data class Success<out T>(val data:T?=null): Resource<T>()
    data class Loading(val nothing: Nothing? =null): Resource<Nothing>()
    data class Error<out T> ( val data:T?=null , val error:String?=null): Resource<T>()
}