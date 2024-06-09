import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

class MainScreen(tasksGroups: MutableList<TasksGroup>, val dataManager: DataManager, var username: String) {

    var groupsList = tasksGroups.toMutableStateList()


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Run(){
        val ui = MainScreenUI(dataManager)

        var uncompletedTasks by remember {
            mutableIntStateOf(getNumberOfIncompleteTasks())
        }

        var showDialog by remember {
        mutableStateOf(false)
        }


        fun <T> MutableList<T>.moveToFront(element: T) {
            if (this.remove(element)) {
                this.add(0, element)
                dataManager.saveInformation(groupsList)
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
                                            uncompletedTasks = getNumberOfIncompleteTasks()},
                                        startingValue = taskGroup.showTasks,
                                        onClick = {
                                            taskGroup.onTop = !taskGroup.onTop
                                            if (taskGroup.onTop){
                                                groupsList.moveToFront(taskGroup)
                                            } else {
                                                // Find the correct position to insert when onTop is false
                                                if (groupsList.size > 1) {
                                                    groupsList.remove(taskGroup)  // Remove taskGroup before re-inserting it
                                                    var index = 0

                                                    // Loop to find the last onTop = true object
                                                    while (index < groupsList.size && groupsList[index].onTop) {
                                                        index++
                                                    }

                                                    // Insert the taskGroup right after the last onTop = true object
                                                    groupsList.add(index, taskGroup)
                                                } else {
                                                    groupsList.moveToFront(taskGroup)
                                                }
                                            }
                                        }
                                    )
                            }
                            Spacer(modifier = Modifier.padding(20.dp))

                        }
                    }

                    if (showDialog){
                        ui.AddNewTaskGroupDialog(
                            onDismissRequest = {showDialog = false},
                            tasksGroup = groupsList,
                            saveData = {dataManager.saveInformation(groupsList)}
                        )
                    }
                }
                ui.AddNewGroupButton { showDialog = true }
            }
            if (groupsList.size == 0){
                ui.NoTaskMessage()
            }
        }
    }

    private fun getNumberOfIncompleteTasks(): Int {
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