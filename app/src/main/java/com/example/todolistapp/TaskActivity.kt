package com.example.todolistapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"
class TaskActivity : AppCompatActivity() {

    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    lateinit var calendar: Calendar

    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    private lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener

    var finalDate = 0L

    var finalTime = 0L


    private val labels = arrayListOf("Personal", "Work", "Family", "Relaxation")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        dateEdit.setOnClickListener{
            setDateListener()
        }
        timeEdit.setOnClickListener {
            setTimeListener()
        }
        saveBtn.setOnClickListener{
            saveBtnListener()
        }
        setUpSpinner()
    }

    private fun saveBtnListener() {
        val category = spinnerCategory.selectedItem.toString()
        val description = taskInputLayout.editText?.text.toString()
        val title = titleInputLayout.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main){
            val id = withContext(Dispatchers.IO){
                val newTodo = Todo(
                    title,
                    description,
                    finalDate,
                    finalTime,
                    category
                );
                return@withContext db.todoDao().insertTask(
                    newTodo
                )
            }
            finish()
        }
    }

    private fun updateTime() {
        val timePattern = "HH:mm"
        val timeFormat = SimpleDateFormat(timePattern).format(calendar.time)
        finalTime = calendar.time.time
        timeEdit.setText(timeFormat.format(calendar))
    }

    private fun setTimeListener() {
        calendar = Calendar.getInstance()
        timeSetListener = TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            updateTime()
        }
        Log.d("HEREHEHEHEHE",calendar.get(Calendar.HOUR_OF_DAY).toString())
        val timePickerDialog = TimePickerDialog(this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),false
            );
        timePickerDialog.show()
    }

    private fun setDateListener(){
        calendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _:DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate()
        }

        val datePickerDialog = DatePickerDialog(this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        val datePattern = "EEE, dd MMM yyyy"
        val dateFormat = SimpleDateFormat(datePattern)
        finalDate = calendar.time.time
        dateEdit.setText(dateFormat.format(calendar.time))
        timeInputLayout.visibility = View.VISIBLE
    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        spinnerCategory.adapter = adapter
    }

}

