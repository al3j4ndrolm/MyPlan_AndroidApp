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

class MainScreen {
    private var groupsList = mutableStateListOf<TasksGroup>(TasksGroup("Math", "1A"))

    @Composable
    fun run(){
        val ui = MainScreenUI()

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
                        tasksGroup = groupsList
                    )
                }
            }
        }
    }
}