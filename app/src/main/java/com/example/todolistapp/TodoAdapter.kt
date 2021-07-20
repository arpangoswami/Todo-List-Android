package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class TodoAdapter(val list: List<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
        )
    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(todoModel: Todo) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random.nextInt(colors.size)]
                viewColorTag.setBackgroundColor(randomColor)
                txtShowTitle.text = todoModel.title
                txtShowTask.text = todoModel.description
                txtShowCategory.text = todoModel.category
                updateTime(todoModel.time)
                updateDate(todoModel.date)
            }
        }
        private fun updateTime(time: Long) {
            //8:23 pm
            val timePattern = "h:mm a"
            val timeFormat = SimpleDateFormat(timePattern)
            itemView.txtShowTime.text = timeFormat.format(Date(time))
        }

        private fun updateDate(time: Long) {
            //Mon, 5 Jan 2020
            val datePattern = "EEE, d MMM yyyy"
            val dateFormat = SimpleDateFormat(datePattern)
            itemView.txtShowDate.text = dateFormat.format(Date(time))
        }
    }
}