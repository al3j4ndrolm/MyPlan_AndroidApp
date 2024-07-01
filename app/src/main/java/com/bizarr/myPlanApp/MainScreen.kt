package com.bizarr.myPlanApp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainScreen(
    tasksGroups: MutableList<TasksGroup>,
    val dataManager: DataManager,
    var username: String,
    var isDialogOpen: Boolean,
    ){
    private var groupsList = tasksGroups.toMutableStateList()



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Run(){
        val ui = AppUI(
            dataManager = dataManager,
        )

        val context = LocalContext.current
        var uncompletedTasks by remember {
            mutableIntStateOf(getNumberOfIncompleteTasks())
        }

        var showDialog by remember {
        mutableStateOf(isDialogOpen)
        }

        
        fun <T> MutableList<T>.moveToFront(element: T) {
            if (this.remove(element)) {
                this.add(0, element)
                dataManager.saveTaskGroupsInformation(groupsList)
            }
        }



        Box(modifier = Modifier
            .fillMaxSize()
        ){

            Box(modifier = Modifier.fillMaxWidth()) {
                ui.MainScreenBackground()
                Column {
                    ui.MainScreenHeader(uncompletedTasks, username)
                        Column(modifier = Modifier
                            .padding(top = 10.dp)
                            .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (groupsList.size != 0 ){
                                for (taskGroup in groupsList){
                                    ui.TaskGroupContainer(
                                        tasksGroupInstance = taskGroup,
                                        tasksGroup = groupsList,
                                        updateUncompletedTask = {
                                            uncompletedTasks = getNumberOfIncompleteTasks()}
                                    ) {
                                        taskGroup.onTop = !taskGroup.onTop
                                        if (taskGroup.onTop) {
                                            groupsList.moveToFront(taskGroup)
                                        } else {

                                            if (groupsList.size > 1) {
                                                groupsList.remove(taskGroup)
                                                var index = 0

                                                while (index < groupsList.size && groupsList[index].onTop) {
                                                    index++
                                                }

                                                groupsList.add(index, taskGroup)
                                            } else {
                                                groupsList.moveToFront(taskGroup)
                                            }
                                        }
                                    }
                                }
                            Spacer(modifier = Modifier.padding(20.dp))

                        }
                    }

                    if (showDialog){
                        ui.AddNewTaskGroupDialog(
                            onDismissRequest = {
                                showDialog = false
                                dataManager.saveShowAddNewTaskGroupDialog(showDialog)
                                               },
                            tasksGroup = groupsList,
                            saveData = {dataManager.saveTaskGroupsInformation(groupsList)}
                        )
                    }
                }
                ui.AddNewGroupButton {
                    showDialog = true
                    dataManager.saveShowAddNewTaskGroupDialog(showDialog)
                }
            }
            if (groupsList.size == 0){
                ui.NoTaskMessage()
            }
        }
        LaunchedEffect(key1 = true) {
            NotificationScheduler
                .scheduleNotification(
                    context,
                    hour = 10,
                    minute = 57
                )
        }
    }

    @JvmName("getNumberOfIncompleteTasksMethod")
    fun getNumberOfIncompleteTasks(): Int {
        var incompleteTasksCountKeeper = 0

        for (taskGroup in groupsList){
            for (task in taskGroup.taskList){
                if (!task.getState()){
                    incompleteTasksCountKeeper++
                }
            }
        }
        return incompleteTasksCountKeeper
    }
}