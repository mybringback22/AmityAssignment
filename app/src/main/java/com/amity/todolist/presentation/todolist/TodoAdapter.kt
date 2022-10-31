package com.amity.todolist.presentation.todolist

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amity.todolist.R
import com.amity.todolist.databinding.ItemTodoBinding
import com.amity.todolist.domain.model.Todo

class TodoAdapter ( private val todos : List<Todo> , private  val context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
    class ViewHolder(var binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.todoTitle.text = todos[position].title
        if(todos[position].completeStatus){
            holder.binding.statusValue.text = context.getString(R.string.completed)
            setTextColor( holder.binding.statusValue , R.color.green )
        }else{
            holder.binding.statusValue.text = context.getString(R.string.pending)
            setTextColor( holder.binding.statusValue , R.color.dark_gray)
        }
    }

    override fun getItemCount(): Int {
        return  todos.size
    }

    private fun setTextColor(textView : TextView, color : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(context.resources.getColor(color , null))
        }else{
            textView.setTextColor(context.resources.getColor(color))
        }
    }
}