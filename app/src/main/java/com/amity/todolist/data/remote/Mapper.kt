package com.amity.todolist.data.remote

import com.amity.todolist.data.remote.model.TodoDTO
import com.amity.todolist.domain.model.Todo

fun TodoDTO.toTodo(): Todo{
    return Todo( id , title ,completed )
}