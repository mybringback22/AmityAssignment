package com.amity.todolist.data.remote.model

import com.google.gson.annotations.SerializedName

class TodoDTO (
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("completed")
    var completed: Boolean,
)