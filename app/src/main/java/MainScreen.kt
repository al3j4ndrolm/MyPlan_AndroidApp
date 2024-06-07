import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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


    @Composable
    fun Run(){
        val ui = MainScreenUI(dataManager)

        var uncompletedTasks by remember {
            mutableIntStateOf(getNumberOfIncompleteTasks())
        }

        var showDialog by remember {
        mutableStateOf(false)
        }

        Box(modifier = Modifier
            .fillMaxSize()
        ){

            Box(modifier = Modifier.fillMaxSize()) {
                ui.MainScreenBackground()
                Column {
                    ui.MainScreenHeader(uncompletedTasks)
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
                                    startingValue = taskGroup.showTasks
                                )
                            }
                        }

                    }
                }

                ui.AddNewGroupButton { showDialog = true }
                if (showDialog){
                    ui.AddNewTaskGroupDialog(
                        onDismissRequest = {showDialog = false},
                        tasksGroup = groupsList,
                        saveData = {dataManager.saveInformation(groupsList)}
                    )
                }
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