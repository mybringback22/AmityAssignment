package com.amity.todolist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amity.todolist.domain.model.Todo

@Dao
interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos( todos : List<Todo> )

    @Query("SELECT * FROM table_todo")
    suspend fun getAllTodos(): List<Todo>

    @Query("DELETE FROM table_todo")
    fun deleteAll()
}