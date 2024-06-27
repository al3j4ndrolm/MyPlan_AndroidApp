package com.bizarr.myPlanApp

import androidx.compose.runtime.mutableStateListOf

data class TasksGroup(var taskGroupName: String, var taskGroupNumber: String = "") {
    var taskList = mutableStateListOf<Task>()
    var onTop = false
    var showTasks: Boolean = false
    var isShowTaskDialogOpen = false
    var lastTaskWritten = ""

    fun setNewTaskGroupName(newName: String){
        this.taskGroupName = newName
    }

    fun addNewTask(task: String){
        taskList.add(Task(task))
    }

    fun removeTask(task: Task){
        taskList.remove(task)
    }

    fun clearTaskGroup(){
        taskList.clear()
    }

    fun getShowTask(): Boolean {
        return this.showTasks
    }

    fun setShowTask(){
        this.showTasks = !showTasks
    }

    fun setLastTaskWroten(text: String){
        this.lastTaskWritten = text
    }
}