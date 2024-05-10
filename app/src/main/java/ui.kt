import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.totd_final.R

class MainScreenUI {

    @Composable
    fun TaskGroupContainer(tasksGroupInstance: TasksGroup, tasksGroup: MutableList<TasksGroup>) {
        var showDialog by remember {
            mutableStateOf(false)
        }

        var showTasks by remember {
            mutableStateOf(false)
        }

        var showDeleteGroup by remember {
            mutableStateOf(false)
        }


        Box(
            modifier = Modifier
                .background(color = Color.LightGray)
                .size(200.dp, 90.dp)
        ) {
            Column {
                Text(
                    text = "${tasksGroupInstance.taskGroupName} - Pending task: ${tasksGroupInstance.taskList.size}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { showDeleteGroup = true },
                                onTap = { showTasks = !showTasks }
                            )
                        }
                )
                if (showTasks) {
                    for (task in tasksGroupInstance.taskList) {
                        taskElement(
                            task = task,
                            tasksGroupInstance = tasksGroupInstance
                        )
                    }

                    addNewTaskButton {
                        showDialog = true
                    }
                    if (showDialog) {
                        addNewTaskDialog(
                            tasksGroupInstance,
                            onDismissRequest = { showDialog = false })
                    }
                }

                if (showDeleteGroup){
                    deleteGroupDialog(
                        onDismissRequest = { showDeleteGroup = false },
                        onClick = { tasksGroup.remove(tasksGroupInstance) }
                    )
                }
            }
        }
    }

    @Composable
    fun taskElement(task: Task, tasksGroupInstance: TasksGroup) {

        var showDeleteTaskDialog by remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .size(200.dp, 20.dp)
                .background(color = Color.LightGray)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            showDeleteTaskDialog = true
                        }
                    )
                }
        ) {
            Row {
                Checkbox(checked = false, onCheckedChange = null)
                Text(
                    text = task.getTaskDescription()
                )
            }
        }

        if (showDeleteTaskDialog){
            deleteTaskDialog(
                onDismissRequest = { showDeleteTaskDialog = false },
                onClick = {tasksGroupInstance.removeTask(task)}
            )
        }
    }

    @Composable
    fun noTaskMessage() {
        Text(text = "There's no task")
    }

    @Composable
    fun addNewGroupButton(onClick: () -> Unit) {
        Button(onClick = { onClick() }) {
            Text(text = "Add new group")
        }
    }

    @Composable
    fun addNewTaskButton(onClick: () -> Unit) {
        Button(onClick = { onClick() }) {
            Text(text = "Add new task")
        }
    }

    @Composable
    fun addNewTaskGroupDialog(onDismissRequest: () -> Unit, tasksGroup: MutableList<TasksGroup>) {
        var text by remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(190.dp)
            ) {
                Column {
                    Text(text = "Add new group")
                    TextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Button(onClick = {
                        tasksGroup.add(TasksGroup(text))
                        onDismissRequest()
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }

    @Composable
    fun addNewTaskDialog(tasksGroupInstance: TasksGroup, onDismissRequest: () -> Unit) {
        var text by remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(190.dp)
            ) {
                Column {
                    Text(text = "Add new task")
                    TextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Button(onClick = {
                        tasksGroupInstance.addNewTask(text)
                        onDismissRequest()
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }

    @Composable
    fun mainScreenBackground() {
        Image(
            painter = painterResource(R.drawable.wallpaper),
            contentDescription = "Wallpaper",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun mainScreenHeader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.hsl(0F, .76F, .29F))
        ) {
            Row {
                Column {
                    Box(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                        Text(text = "March 29, 2024\nWelcome back\nAlejandro")
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.dotted_line),
                    contentDescription = "Vertical line",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .size(100.dp),
                    colorFilter = ColorFilter.tint(Color.hsl(36F, 1F, .67F))
                )
                Column {
                    Box(modifier = Modifier
                        .padding(top = 20.dp, end = 20.dp)
                        .size(180.dp)) {
                        Text(text = "You have 5\nassignments left.")
                    }
                }
            }
        }
    }

    @Composable
    fun deleteGroupDialog(onDismissRequest: () -> Unit, onClick: () -> Unit) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(190.dp)
            ) {
                Column {
                    Text(text = "Are you sure you want to delete this group")
                    Row {
                        Button(onClick = {onDismissRequest()}) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            onClick()
                            onDismissRequest()
                        }) {
                            Text(text = "Delete")
                        }
                    }

                }
            }
        }
    }
    @Composable
    fun deleteTaskDialog(onDismissRequest: () -> Unit, onClick: () -> Unit) {

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(190.dp)
            ) {
                Column {
                    Text(text = "Are you sure you want to delete this task")
                    Row {
                        Button(onClick = {onDismissRequest()}) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            onClick()
                            onDismissRequest()
                        }) {
                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
    }
}