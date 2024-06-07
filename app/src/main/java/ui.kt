import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.totd_final.R

class MainScreenUI(dataManager: DataManager) {
    private val yellowColor = Color.hsl(36F, 1F, .67F)
    private val darkLightYellow = Color.hsl(36F, .54F, .71F)
    private val redWineColor = Color.hsl(0F, .76F, .29F)
    private val lightYellowColor = Color.hsl(36F, 1F, .84F)
    private val fontFamily = FontFamily(Font(R.font.readexpro))
    val dataManager = dataManager



    @Composable
    fun TaskGroupContainer(tasksGroupInstance: TasksGroup, tasksGroup: MutableList<TasksGroup>, updateUncompletedTask: () -> Unit, startingValue: Boolean) {
        var showAddTaskDialog by remember {
            mutableStateOf(false)
        }

        var showTasks by remember {
            mutableStateOf(startingValue)
        }

        var showDeleteGroup by remember {
            mutableStateOf(false)
        }

        var containerSize by remember {
            mutableStateOf(250)
        }

        if (showTasks){
            containerSize = 350
        }

        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(
                    color = yellowColor,
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .width(containerSize.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { showDeleteGroup = true },
                        onTap = {
                            showTasks = !showTasks
                            tasksGroupInstance.setShowTask()
                        }
                    )
                }

        ) {
            Column {
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    Column {
                        if (!showTasks){
                            containerSize = 250
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Light,
                                        color = redWineColor
                                    )
                                    ) {
                                        append(tasksGroupInstance.taskGroupName)
                                    }
                                    append("\n")
                                    withStyle(style = SpanStyle(
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = redWineColor
                                    )) {
                                        append(tasksGroupInstance.taskGroupNumber)
                                    }
                                },
                                style = TextStyle(
                                    lineHeight = 36.sp,
                                    fontFamily = fontFamily,
                                    fontSize = 35.sp),
                            )
                            dataManager.saveInformation(tasksGroup)
                        }
                    }
                }

                if (showTasks) {
                    containerSize = 350
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Light,
                                color = redWineColor
                            )
                            ) {
                                append(tasksGroupInstance.taskGroupName)
                            }
                            append(" ")
                            withStyle(style = SpanStyle(
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                color = redWineColor
                            )) {
                                append(tasksGroupInstance.taskGroupNumber)
                            }
                        },style = TextStyle(
                            lineHeight = 36.sp,
                            fontFamily = fontFamily,
                            fontSize = 35.sp),
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { showDeleteGroup = true },
                                    onTap = {
                                        showTasks = !showTasks
                                        tasksGroupInstance.setShowTask()
                                    }
                                )
                            }
                            .padding(start = 8.dp)
                    )


                    for (task in tasksGroupInstance.taskList) {
                        taskElement(
                            task = task,
                            tasksGroupInstance = tasksGroupInstance,
                            tasksGroup = tasksGroup,
                            updateUncompletedTask = { updateUncompletedTask() }
                        )
                    }

                    addNewTaskButton {
                        showAddTaskDialog = true
                    }
                    if (showAddTaskDialog) {
                        addNewTaskDialog(
                            tasksGroupInstance,
                            onDismissRequest = { showAddTaskDialog = false },
                            tasksGroup = tasksGroup,
                            updateUncompletedTask = updateUncompletedTask
                            )
                    }
                    dataManager.saveInformation(tasksGroup)
                }

                if (showDeleteGroup) {
                    deleteGroupDialog(
                        onDismissRequest = { showDeleteGroup = false },
                        onClick = {
                            tasksGroup.remove(tasksGroupInstance)
                            dataManager.saveInformation(tasksGroup)
                            updateUncompletedTask()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun taskElement(task: Task, tasksGroupInstance: TasksGroup, tasksGroup: MutableList<TasksGroup>, updateUncompletedTask: () -> Unit) {

        var showDeleteTaskDialog by remember {
            mutableStateOf(false)
        }

        var status by remember {
            mutableStateOf(task.getState())
        }

        Box(
            modifier = Modifier
                .background(color = yellowColor)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = { showDeleteTaskDialog = true })
                }
                .padding(bottom = 12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = status,
                    onCheckedChange = {
                        status = !status
                        task.saveCheckState(status)
                        updateUncompletedTask()
                        dataManager.saveInformation(tasksGroup)
                    },
                    modifier = Modifier
                        .alignByBaseline()
                        .size(24.dp)
                        .padding(start = 12.dp)
                )

                Text(
                    text = task.getTaskDescription(),
                    modifier = Modifier
                        .alignByBaseline()
                        .padding(start = 8.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { showDeleteTaskDialog = true },
                                onTap = {
                                    status = !status
                                    task.saveCheckState(status)
                                    updateUncompletedTask()
                                    dataManager.saveInformation(tasksGroup)
                                }
                            )
                        }
                )
            }
        }

        if (showDeleteTaskDialog) {
            deleteTaskDialog(
                onDismissRequest = { showDeleteTaskDialog = false },
                onClick = {
                    tasksGroupInstance.removeTask(task)
                    dataManager.saveInformation(tasksGroup)
                },
                updateUncompletedTask = { updateUncompletedTask() }
            )
        }
    }

    @Composable
    fun noTaskMessage() {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    tint = redWineColor,
                    painter = painterResource(id = R.drawable.error_24dp_fill0_wght400_grad0_opsz24),
                    contentDescription = "No task")

                Text(
                    text = "Create a group to start adding tasks",
                    color = redWineColor,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun addNewGroupButton(onClick: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {onClick() },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                colors = ButtonDefaults.buttonColors(redWineColor)
            ) {
                Text(
                    text = "Add new group",
                    color = yellowColor
                )
            }
        }
    }

    @Composable
    fun addNewTaskButton(onClick: () -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(yellowColor)
            ) {
                Text(
                    text = "Add new task +",
                    color = redWineColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun addNewTaskGroupDialog(onDismissRequest: () -> Unit, tasksGroup: MutableList<TasksGroup>?, saveData: () -> Unit) {
        var text by remember {
            mutableStateOf("")
        }

        var number by remember {
            mutableStateOf("")
        }

        var isInvalidInput by remember {
            mutableStateOf(false)
        }

        var isDuplicateInput by remember {
            mutableStateOf(false)
        }

        var showInputError by remember {
            mutableStateOf(false)
        }

        var showDuplicateError by remember {
            mutableStateOf(false)
        }

        fun validateInput(value: String, number: String): Boolean{
            if (value.isEmpty()){
                isInvalidInput = true
                return true
            }
            else if (value.length > 8){
                isInvalidInput = true
                return true
            }
            else if (tasksGroup != null) {
                if (tasksGroup.isNotEmpty()) {
                    if (tasksGroup.contains(TasksGroup(value, number))) {
                        isDuplicateInput = true
                        return true
                    }
                }
            }
            isInvalidInput = false
            isDuplicateInput = false
            return false
        }

        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Box(
                modifier = Modifier
                    .background(
                        yellowColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(width = 290.dp, height = 260.dp)
            ) {
                Column {
                    Text(
                        text = "Add new group",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.readexpro)),
                            fontSize = 20.sp,
                            color = redWineColor
                        )
                    )
                    TextField(
                        value = text,
                        onValueChange = {
                            newText -> text = newText
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = lightYellowColor
                        ),
                        label = { Text(text = "Class name") },
                        placeholder = { Text(
                            text = "Math",
                            color = darkLightYellow) }
                    )
                    TextField(
                        value = number,
                        onValueChange = { newText -> number = newText },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = lightYellowColor
                        ),
                        label = { Text(text = "Class number") },
                        placeholder = {
                            Text(text = "1A",
                            color = darkLightYellow) }
                    )
                    if (showInputError){
                        Text(
                            text = "Hey! The class can't be empty or bigger than 8 letters",
                            color = redWineColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp, start = 4.dp, end = 2.dp)
                        )
                    }
                    else if (showDuplicateError) {
                        showInputError = false
                        Text(
                            text = "Careful! This group already exist",
                            color = redWineColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp, start = 4.dp, end = 2.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                    ){
                        Button(
                            colors = ButtonDefaults.buttonColors(redWineColor),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(
                                    color = redWineColor,
                                    shape = RoundedCornerShape(
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )

                                ),
                            onClick = {
                                validateInput(text, number)
                                if (isInvalidInput){
                                    showInputError = true
                                }
                                else if (isDuplicateInput){
                                    showDuplicateError = true
                                }
                                else {
                                    tasksGroup?.add(TasksGroup(text, number))
                                    saveData()
                                    onDismissRequest()
                                }
                            }) {
                            Text(text = "Save",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.readexpro)),
                                    fontSize = 15.sp,
                                    color = yellowColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun addNewTaskDialog(tasksGroupInstance: TasksGroup, onDismissRequest: () -> Unit, tasksGroup: MutableList<TasksGroup>, updateUncompletedTask: () -> Unit) {
        var text by remember {
            mutableStateOf("")
        }

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(
                        yellowColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(width = 290.dp, height = 170.dp)
            ) {
                Column {
                    Text(
                        text = "Add new task",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.readexpro)),
                            fontSize = 20.sp,
                            color = redWineColor
                        )
                    )
                    TextField(
                        value = text,
                        onValueChange = { newText -> text = newText },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = lightYellowColor
                        ),
                        label = { Text(text = "Task description") },
                        placeholder = { Text(
                            text = "Finish Lab 3 - 3D Modeling",
                            color = darkLightYellow)
                        }
                    )
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ){
                        Button(
                            onClick = {
                            tasksGroupInstance.addNewTask(text)
                            onDismissRequest()
                            dataManager.saveInformation(tasksGroup)
                            updateUncompletedTask() },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(
                                    color = redWineColor,
                                    shape = RoundedCornerShape(
                                        bottomStart = 16.dp,
                                        bottomEnd = 16.dp
                                    )

                                ),
                            colors = ButtonDefaults.buttonColors(redWineColor)
                        ) {
                            Text(text = "Save",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.readexpro)),
                                    fontSize = 15.sp,
                                    color = yellowColor
                                )
                            )
                        }
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
    fun mainScreenHeader(uncompletedTask: Int) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(redWineColor)
        ) {
            Row {
                Column(Modifier.padding(start = 9.dp, top = 8.dp)) {
                    Box {
                        Text(
                            text = "March 29, 2024",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.readexpro)),
                                fontSize = 12.sp,
                                color = yellowColor
                            )
                        )
                    }
                    Box {
                        Text(
                            text = "Welcome back\nAlejandro",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.readexpro)),
                                fontSize = 20.sp,
                                color = yellowColor
                            )
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.dotted_line),
                    contentDescription = "Vertical line",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .size(100.dp),
                    colorFilter = ColorFilter.tint(yellowColor)
                )
                Column {
                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp, end = 25.dp)
                            .size(180.dp)
                    ) {

                        Text(
                            text = "You have $uncompletedTask assignment left.",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.readexpro)),
                                fontSize = 18.sp,
                                color = yellowColor
                            )
                        )
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
                    .background(
                        yellowColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(200.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.priority_high_24dp_fill0_wght400_grad0_opsz24),
                        contentDescription = "Exclamation",
                        modifier = Modifier
                            .padding(top = 10.dp),
                        tint = redWineColor
                    )
                    Text(
                        text = "Are you sure you want to delete this group?",
                        modifier = Modifier.padding(6.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.readexpro)),
                            fontSize = 20.sp,
                            color = redWineColor,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                        ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.background(redWineColor, shape = RoundedCornerShape(bottomStart = 16.dp))

                            ){
                                Button(
                                    onClick = { onDismissRequest() },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomStart = 14.dp, bottomEnd = 0.dp),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .background(redWineColor, shape = RoundedCornerShape(bottomEnd = 16.dp))
                            ) {
                                Button(
                                    onClick = {
                                        onClick()
                                        onDismissRequest()
                                    },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(topStart = 0.dp, topEnd = 14.dp, bottomStart = 0.dp, bottomEnd = 14.dp),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun deleteTaskDialog(onDismissRequest: () -> Unit, onClick: () -> Unit, updateUncompletedTask: () -> Unit) {

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(
                        yellowColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .size(200.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.priority_high_24dp_fill0_wght400_grad0_opsz24),
                        contentDescription = "Exclamation",
                        modifier = Modifier
                            .padding(top = 10.dp),
                        tint = redWineColor
                    )
                    Text(text = "Are you sure you want to delete this task",
                        modifier = Modifier.padding(6.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.readexpro)),
                            fontSize = 20.sp,
                            color = redWineColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.background(redWineColor, shape = RoundedCornerShape(bottomStart = 16.dp))

                            ){
                                Button(
                                    onClick = { onDismissRequest() },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomStart = 14.dp, bottomEnd = 0.dp),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .background(redWineColor, shape = RoundedCornerShape(bottomEnd = 16.dp))
                            ) {
                                Button(
                                    onClick = {
                                        onClick()
                                        onDismissRequest()
                                        updateUncompletedTask()
                                    },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(topStart = 0.dp, topEnd = 14.dp, bottomStart = 0.dp, bottomEnd = 14.dp),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}