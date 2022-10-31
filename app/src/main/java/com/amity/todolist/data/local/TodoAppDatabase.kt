package com.amity.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amity.todolist.domain.model.Todo

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoAppDatabase : RoomDatabase() {
    abstract fun getTodoDAO() : TodoDAO
}