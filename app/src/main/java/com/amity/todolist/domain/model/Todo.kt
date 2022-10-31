package com.amity.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity( tableName = "table_todo")
data class Todo (
    @PrimaryKey
    var id: Int,
    var title: String,
    var completeStatus : Boolean
)