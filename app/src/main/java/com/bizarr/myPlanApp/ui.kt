package com.bizarr.myPlanApp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainScreenUI(private val dataManager: DataManager,) {
    private val yellowColor = Color.hsl(36F, 1F, .67F)
    private val darkLightYellow = Color.hsl(36F, .54F, .71F)
    private val redWineColor = Color.hsl(354F, .95F, .22F)
    private val lightRedWineColor = Color.hsl(354F, .58F, .37F)
    private val lightYellowColor = Color.hsl(36F, 1F, .84F)
    private val fontFamily = FontFamily(Font(R.font.readexpro))

    @Composable
    fun TaskGroupContainer(
        tasksGroupInstance: TasksGroup,
        tasksGroup: MutableList<TasksGroup>,
        updateUncompletedTask: () -> Unit,
        onClick: () -> Unit
    ) {
        var showAddTaskDialog by remember {
            mutableStateOf(tasksGroupInstance.isShowTaskDialogOpen)
        }

        var showTasks by remember {
            mutableStateOf(tasksGroupInstance.showTasks)
        }

        var showDeleteGroup by remember {
            mutableStateOf(false)
        }

        val containerSize by animateDpAsState(
            targetValue = if (showTasks) 350.dp else 250.dp,
            animationSpec = tween(500), label = ""
        )

        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    ),
                    ambientColor = Color.Gray,
                    spotColor = Color.Red
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .width(containerSize)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { showDeleteGroup = true },
                        onTap = {
                            showTasks = !showTasks
                            tasksGroupInstance.setShowTask()
                            dataManager.saveTaskGroupsInformation(tasksGroup)
                        }
                    )
                }

        ) {
            Row {
                Column {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Column {
                            if (!showTasks) {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.Light,
                                                    color = redWineColor
                                                )
                                            ) {
                                                append(tasksGroupInstance.taskGroupName)
                                            }
                                            append("\n")
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = redWineColor
                                                )
                                            ) {
                                                append(tasksGroupInstance.taskGroupNumber)
                                            }
                                        },
                                        style = TextStyle(
                                            lineHeight = 36.sp,
                                            fontFamily = fontFamily,
                                            fontSize = 35.sp
                                        ),
                                    )
                                    if (tasksGroupInstance.taskList.any { !it.getState() }) {
                                        PendingTaskDot()
                                    }
                                }
                                dataManager.saveTaskGroupsInformation(tasksGroup)
                            }
                        }
                    }

                    if (showTasks) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Light,
                                        color = redWineColor
                                    )
                                ) {
                                    append(tasksGroupInstance.taskGroupName)
                                }
                                append(" ")
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = redWineColor
                                    )
                                ) {
                                    append(tasksGroupInstance.taskGroupNumber)
                                }
                            }, style = TextStyle(
                                lineHeight = 36.sp,
                                fontFamily = fontFamily,
                                fontSize = 35.sp
                            ),
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
                            TaskElement(
                                task = task,
                                tasksGroupInstance = tasksGroupInstance,
                                tasksGroup = tasksGroup,
                                updateUncompletedTask = { updateUncompletedTask() }
                            )
                        }

                        if (tasksGroupInstance.taskList.size == 0) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Is empty here! Press \"Add new task\".",
                                    fontFamily = fontFamily,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 13.sp,
                                    color = Color.LightGray
                                )
                            }
                        }

                        AddNewTaskButton {
                            showAddTaskDialog = true
                            tasksGroupInstance.isShowTaskDialogOpen = showAddTaskDialog
                            dataManager.saveTaskGroupsInformation(tasksGroup)
                        }
                        if (showAddTaskDialog) {
                            AddNewTaskDialog(
                                tasksGroupInstance,
                                onDismissRequest = {
                                    showAddTaskDialog = false
                                    tasksGroupInstance.isShowTaskDialogOpen = showAddTaskDialog
                                    dataManager.saveTaskGroupsInformation(tasksGroup)
                                                   },
                                tasksGroup = tasksGroup,
                                updateUncompletedTask = updateUncompletedTask
                            )
                        }
                        dataManager.saveTaskGroupsInformation(tasksGroup)
                    }

                    if (showDeleteGroup) {

                        DeleteGroupDialog(
                            onDismissRequest = { showDeleteGroup = false },
                            onClick = {
                                tasksGroup.remove(tasksGroupInstance)
                                dataManager.saveTaskGroupsInformation(tasksGroup)
                                updateUncompletedTask()
                            },
                            tasksGroupInstance = tasksGroupInstance
                        )
                    }
                }
            }
            BookMark(
                onClick = {
                    onClick()
                },
                onTopValue = tasksGroupInstance.onTop,
                tasksGroupInstanceName = tasksGroupInstance.taskGroupName
            )
        }
    }


    @Composable
    fun PendingTaskDot() {
        Box(
            modifier = Modifier
                .padding(top = 6.dp, start = 4.dp)
                .size(10.dp)
                .background(redWineColor, shape = RoundedCornerShape(16.dp)),
        )
    }

    @Composable
    fun BookMark(onClick: () -> Unit, onTopValue: Boolean, tasksGroupInstanceName: String) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, end = 4.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (onTopValue) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.bookmark_star_24dp_fill0_wght400_grad0_opsz24
                    ),
                    contentDescription = "Activated Bookmark for $tasksGroupInstanceName",
                    tint = redWineColor,
                    modifier = Modifier
                        .clickable {
                            onClick()
                        }
                )
            } else {
                Icon(
                    painter = painterResource(
                        id = R.drawable.bookmark_add_24dp_fill0_wght400_grad0_opsz24
                    ),
                    contentDescription = "Non active Bookmark for $tasksGroupInstanceName",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .clickable {
                            onClick()
                        }
                )
            }
        }
    }

    @Composable
    fun TaskElement(
        task: Task,
        tasksGroupInstance: TasksGroup,
        tasksGroup: MutableList<TasksGroup>,
        updateUncompletedTask: () -> Unit
    ) {

        var showDeleteTaskDialog by remember {
            mutableStateOf(false)
        }

        var status by remember {
            mutableStateOf(task.getState())
        }

        Box(
            modifier = Modifier
                .background(color = Color.White)
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
                        dataManager.saveTaskGroupsInformation(tasksGroup)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = redWineColor,
                        checkmarkColor = Color.White,
                    ),
                    modifier = Modifier
                        .alignByBaseline()
                        .size(24.dp)
                        .padding(start = 12.dp)
                )

                if (status) {
                    Text(
                        text = task.getTaskDescription(),
                        color = lightRedWineColor,
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 16.9.sp
                        ),
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
                                        dataManager.saveTaskGroupsInformation(tasksGroup)
                                    }
                                )
                            }
                    )
                } else {
                    Text(
                        text = task.getTaskDescription(),
                        color = Color.Black,
                        modifier = Modifier
                            .width(300.dp)
                            .alignByBaseline()
                            .padding(start = 8.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { showDeleteTaskDialog = true },
                                    onTap = {
                                        status = !status
                                        task.saveCheckState(status)
                                        updateUncompletedTask()
                                        dataManager.saveTaskGroupsInformation(tasksGroup)
                                    }
                                )
                            }
                    )
                }
            }
        }

        if (showDeleteTaskDialog) {
            DeleteTaskDialog(
                onDismissRequest = { showDeleteTaskDialog = false },
                onClick = {
                    tasksGroupInstance.removeTask(task)
                    dataManager.saveTaskGroupsInformation(tasksGroup)
                },
                updateUncompletedTask = { updateUncompletedTask() }
            )
        }
    }

    @Composable
    fun NoTaskMessage() {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    tint = redWineColor,
                    painter = painterResource(id = R.drawable.new_window_24dp_fill0_wght400_grad0_opsz24),
                    contentDescription = "No task"
                )

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
    fun AddNewGroupButton(onClick: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd),
                colors = ButtonDefaults.buttonColors(redWineColor)
            ) {
                Text(
                    text = "Add new group",
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun AddNewTaskButton(onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(
                    text = "Add new task +",
                    color = redWineColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

    @Composable
    fun AddNewTaskGroupDialog(
        onDismissRequest: () -> Unit,
        tasksGroup: MutableList<TasksGroup>?,
        saveData: () -> Unit
    ) {
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

        fun validateGroupInput(value: String, number: String): Boolean {
            if (value.isEmpty()) {
                isInvalidInput = true
                return true
            } else if (value.length > 8) {
                isInvalidInput = true
                return true
            } else if (tasksGroup != null) {
                if (tasksGroup.any { it.taskGroupName == value && it.taskGroupNumber == number }) {
                    isDuplicateInput = true
                    return true
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
                        onValueChange = { newText ->
                            text = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = lightYellowColor,
                            unfocusedContainerColor = lightYellowColor,
                            disabledContainerColor = lightYellowColor,
                        ),
                        label = { Text(text = "Class name", color = redWineColor) },
                        textStyle = TextStyle(color = Color.Black),
                        placeholder = {
                            Text(
                                text = "Math",
                                color = darkLightYellow
                            )
                        }
                    )
                    TextField(
                        value = number,
                        onValueChange = { newText -> number = newText },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = lightYellowColor,
                            unfocusedContainerColor = lightYellowColor,
                            disabledContainerColor = lightYellowColor,
                        ),
                        label = { Text(text = "Class number", color = redWineColor) },
                        textStyle = TextStyle(color = Color.Black),
                        placeholder = {
                            Text(
                                text = "1A",
                                color = darkLightYellow
                            )
                        }
                    )
                    if (showInputError) {
                        Text(
                            text = "Hey! The class can't be empty or bigger than 8 letters.",
                            color = redWineColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(
                                top = 4.dp,
                                bottom = 6.dp,
                                start = 4.dp,
                                end = 2.dp
                            )
                        )
                    } else if (showDuplicateError) {
                        Text(
                            text = "Careful! This group already exist.",
                            color = redWineColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(
                                top = 4.dp,
                                bottom = 6.dp,
                                start = 4.dp,
                                end = 2.dp
                            )
                        )
                    }

                    CreateNewItemConfirmBox {
                        validateGroupInput(text, number)
                        if (isInvalidInput) {
                            showInputError = true
                        } else if (isDuplicateInput) {
                            showDuplicateError = true
                        } else {
                            tasksGroup?.add(TasksGroup(text, number))
                            saveData()
                            onDismissRequest()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AddNewTaskDialog(
        tasksGroupInstance: TasksGroup,
        onDismissRequest: () -> Unit,
        tasksGroup: MutableList<TasksGroup>,
        updateUncompletedTask: () -> Unit
    ) {
        var text by remember {
            mutableStateOf(tasksGroupInstance.lastTaskWritten)
        }

        var showInvalidInputError by remember {
            mutableStateOf(false)
        }

        fun validateTaskInput(value: String): Boolean {
            return value.isEmpty()
        }

        Dialog(onDismissRequest = {
            onDismissRequest()
            tasksGroupInstance.lastTaskWritten = ""
        }) {
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
                        onValueChange = {
                            newText -> text = newText
                            tasksGroupInstance.lastTaskWritten = text
                            dataManager.saveTaskGroupsInformation(tasksGroup)
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = lightYellowColor,
                            unfocusedContainerColor = lightYellowColor,
                            disabledContainerColor = lightYellowColor,
                        ),
                        label = { Text(text = "Task description", color = redWineColor) },
                        textStyle = TextStyle(color = Color.Black),
                        placeholder = {
                            Text(
                                text = "Finish Lab 3 - 3D Modeling",
                                color = darkLightYellow
                            )
                        }
                    )
                    if (showInvalidInputError) {
                        Text(
                            text = "Oh, the task description can't be empty.",
                            color = redWineColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(
                                top = 4.dp,
                                bottom = 6.dp,
                                start = 4.dp,
                                end = 2.dp
                            )
                        )
                    }

                    CreateNewItemConfirmBox {
                        if (validateTaskInput(text)) {
                            showInvalidInputError = true
                        } else {
                            tasksGroupInstance.lastTaskWritten = ""
                            tasksGroupInstance.addNewTask(text)
                            onDismissRequest()
                            dataManager.saveTaskGroupsInformation(tasksGroup)
                            updateUncompletedTask()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CreateNewItemConfirmBox(
        onClickSave: () -> Unit
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = onClickSave,
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
                Text(
                    text = "Save",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.readexpro)),
                        fontSize = 15.sp,
                        color = Color.White
                    )
                )
            }
        }
    }

    @Composable
    fun MainScreenBackground() {
        Image(
            painter = painterResource(R.drawable.app_background),
            contentDescription = "Wallpaper",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreenHeader(uncompletedTask: Int, username: String) {

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(redWineColor, shape = RoundedCornerShape(bottomEnd = 10.dp))
                        .width(205.dp)
                        .fillMaxHeight()
                ) {
                    Column(Modifier.padding(start = 9.dp, top = 8.dp)) {
                        Box {
                            Text(
                                text = current.format(formatter),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.readexpro)),
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Box(
                            Modifier
                                .background(redWineColor)
                                .clip(shape = RoundedCornerShape(bottomEnd = 10.dp))
                        ) {
                            Text(
                                text = "Welcome back\n$username",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.readexpro)),
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding()
                        .size(180.dp)
                        .background(redWineColor, shape = RoundedCornerShape(bottomStart = 35.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    if (uncompletedTask > 1) {
                        Text(
                            text = "You have $uncompletedTask assignments left.",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.readexpro)),
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "You have $uncompletedTask assignment left.",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.readexpro)),
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun DeleteGroupDialog(
        onDismissRequest: () -> Unit,
        onClick: () -> Unit,
        tasksGroupInstance: TasksGroup
    ) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(
                modifier = Modifier
                    .background(
                        yellowColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .width(200.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.priority_high_24dp_fill0_wght400_grad0_opsz24),
                        contentDescription = "Exclamation",
                        modifier = Modifier
                            .padding(top = 10.dp),
                        tint = redWineColor
                    )

                    val groupErased: String = if (tasksGroupInstance.taskGroupNumber.isEmpty()) {
                        tasksGroupInstance.taskGroupName
                    } else {
                        "${tasksGroupInstance.taskGroupName} - ${tasksGroupInstance.taskGroupNumber}"
                    }

                    Text(
                        text = "Are you sure you want to delete\nthe group:\n\"$groupErased\"",
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
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.background(
                                    redWineColor,
                                    shape = RoundedCornerShape(bottomStart = 16.dp)
                                )

                            ) {
                                Button(
                                    onClick = { onDismissRequest() },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 14.dp,
                                        bottomEnd = 0.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(
                                        text = "Cancel",
                                        color = Color.White
                                    )
                                }
                            }
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .background(
                                        redWineColor,
                                        shape = RoundedCornerShape(bottomEnd = 16.dp)
                                    )
                            ) {
                                Button(
                                    onClick = {
                                        onClick()
                                        onDismissRequest()
                                    },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 14.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 14.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(
                                        text = "Delete",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DeleteTaskDialog(
        onDismissRequest: () -> Unit,
        onClick: () -> Unit,
        updateUncompletedTask: () -> Unit
    ) {

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
                        text = "Are you sure you want to delete this task",
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
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.background(
                                    redWineColor,
                                    shape = RoundedCornerShape(bottomStart = 16.dp)
                                )

                            ) {
                                Button(
                                    onClick = { onDismissRequest() },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 14.dp,
                                        bottomEnd = 0.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(
                                        text = "Cancel",
                                        color = Color.White
                                    )
                                }
                            }
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .background(
                                        redWineColor,
                                        shape = RoundedCornerShape(bottomEnd = 16.dp)
                                    )
                            ) {
                                Button(
                                    onClick = {
                                        onClick()
                                        onDismissRequest()
                                        updateUncompletedTask()
                                    },
                                    modifier = Modifier.width(99.dp),
                                    shape = CutCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 14.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 14.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(redWineColor)
                                ) {
                                    Text(
                                        text = "Delete",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}