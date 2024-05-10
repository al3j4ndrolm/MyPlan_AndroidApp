import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainScreen {
    private val groupsList = mutableListOf<TasksGroup>()

    @Composable
    fun run(){
        var showDialog by remember {
        mutableStateOf(false)
        }

        Box(modifier = Modifier
            .fillMaxSize()
        ){
            Box {
                MainScreenUI().mainScreenBackground()
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    MainScreenUI().mainScreenHeader()
                    if (groupsList.size != 0 ){
                        for (taskGroup in groupsList){
                            MainScreenUI().taskGroupContainer(tasksGroupInstance = taskGroup)
                        }
                    } else {
                        MainScreenUI().noTaskMessage()
                    }
                    MainScreenUI().addNewGroupButton { showDialog = true }
                }
                if (showDialog){
                    MainScreenUI(groupsList).addNewTaskGroupDialog(
                        onDismissRequest = {showDialog = false}
                    )
                }
            }
        }
    }
}