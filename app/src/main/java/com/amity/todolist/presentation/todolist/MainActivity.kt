package com.amity.todolist.presentation.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amity.todolist.databinding.ActivityMainBinding
import com.amity.todolist.domain.model.Todo
import com.amity.todolist.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private var todoAdapter: TodoAdapter? = null
    private lateinit var todosList : List<Todo>
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getTodos().observe(this , todoObserver)
    }

    private fun initTodoAdapter(todos: List<Todo>) {
        todosList = todos
        if(todoAdapter==null) {
            todoAdapter = TodoAdapter(
                todosList,
                this
            )
            linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = linearLayoutManager
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = todoAdapter
        }else{
            todoAdapter?.notifyDataSetChanged()
        }
    }

    private val todoObserver =  Observer<Resource<List<Todo>>> { todoResponse->
        when(todoResponse){
            is Resource.Loading ->{
                binding.progress.visibility = View.VISIBLE
                binding.textViewMsg.visibility = View.GONE
            }
            is Resource.Success ->{
                binding.progress.visibility = View.GONE
                binding.textViewMsg.visibility = View.GONE
                todoResponse.data?.let { todo ->
                    initTodoAdapter(todo)
                }
            }
            is Resource.Error ->{
                todoResponse.data?.let { todo ->
                    if(todo.isNotEmpty()){
                        todoResponse.error?.let {
                            Snackbar.make(binding.recyclerView, it, Snackbar.LENGTH_LONG)
                                .setAction("Retry") {
                                    retry()
                                }
                                .show()
                        }
                    }else{
                        binding.textViewMsg.visibility = View.VISIBLE
                        binding.textViewMsg.text = todoResponse.error
                    }
                }?: kotlin.run {
                    binding.textViewMsg.visibility = View.VISIBLE
                    binding.textViewMsg.text = todoResponse.error
                }
            }
        }
    }

    private fun retry(){
        viewModel.getTodos().observe(this , todoObserver)
    }
}