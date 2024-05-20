import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainScreen(tasksGroups: MutableList<TasksGroup>, dataManager: DataManager) {

    val dataManager = dataManager
    var groupsList = tasksGroups.toMutableStateList()
    val taskGroupLists = TaskGroupLists(groupsList)


    @Composable
    fun Run(){
        val ui = dataManager.let { MainScreenUI(it, taskGroupLists) }

        var showDialog by remember {
        mutableStateOf(false)
        }

        var uncompletedTask by remember {
            mutableIntStateOf(taskGroupLists.getNumberOfIncompleteTasks())
        }

        Box(modifier = Modifier
            .fillMaxSize()
        ){
            Box(modifier = Modifier.fillMaxSize()) {
                ui.mainScreenBackground()
                Column(modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                    ) {
                    ui.mainScreenHeader(uncompletedTask)
                    if (groupsList.size != 0 ){
                        for (taskGroup in groupsList){
                            ui.TaskGroupContainer(
                                    tasksGroupInstance = taskGroup,
                                    tasksGroup = groupsList,
                                    updateUncompletedTask = {
                                        uncompletedTask = taskGroupLists.getNumberOfIncompleteTasks()
                                    }
                                )
                        }
                    } else {
                        ui.noTaskMessage()
                    }
                    ui.addNewGroupButton { showDialog = true }
                }
                if (showDialog){
                    ui.addNewTaskGroupDialog(
                            onDismissRequest = {showDialog = false},
                            tasksGroup = groupsList
                        ) { dataManager.saveInformation(groupsList) }
                }
            }
        }
    }
}