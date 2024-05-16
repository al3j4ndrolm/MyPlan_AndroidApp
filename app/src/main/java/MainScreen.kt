import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainScreen(tasksGroups: List<TasksGroup>, val dataManager: DataManager) {

    var groupsList = mutableStateListOf<TasksGroup>()
    private val tasksGroups = tasksGroups

    @Composable
    fun Run(){
        val ui = MainScreenUI()

        for (taskGroup in tasksGroups){
            groupsList.add(taskGroup)
        }
        dataManager.saveInformation(groupsList)

        var showDialog by remember {
        mutableStateOf(false)
        }

        Box(modifier = Modifier
            .fillMaxSize()
        ){
            Box(modifier = Modifier.fillMaxSize()) {
                ui.mainScreenBackground()
                Column(modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                    ) {
                    ui.mainScreenHeader()
                    if (groupsList.size != 0 ){
                        for (taskGroup in groupsList){
                            ui.TaskGroupContainer(
                                tasksGroupInstance = taskGroup,
                                tasksGroup = groupsList
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
                        tasksGroup = groupsList,
                        saveData = {}
                    )

                }
            }
        }
    }
}