package com.example.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(todo: Todo): Long

    @Query(value = "SELECT * FROM Todo WHERE isFinished == 0")
    fun getAllUnfinishedTask():LiveData<List<Todo>>

    @Query(value = "SELECT * FROM Todo WHERE isFinished == 1")
    fun getAllFinishedTask():LiveData<List<Todo>>

    @Query(value = "UPDATE Todo SET isFinished = 1 WHERE id=:uid")
    fun finishTask(uid:Long)

    @Query(value = "DELETE FROM Todo WHERE id = :uid")
    fun deleteTask(uid: Long)
}