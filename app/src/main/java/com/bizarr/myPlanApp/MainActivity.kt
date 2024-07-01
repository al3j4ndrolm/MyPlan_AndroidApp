package com.bizarr.myPlanApp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bizarr.myPlanApp.theme.TOTD_FinalTheme

/*
The class below is the class that runs the code and work in the device
 */

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TOTD_FinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dataManager = DataManager(this)
                    val signInScreen = SignInScreen()

                    var isSignIn by remember {
                        mutableStateOf(dataManager.readSignInDataFile().isSignIn)
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val name = "Daily Notification"
                        val descriptionText = "Daily Notification Channel"
                        val importance = NotificationManager.IMPORTANCE_DEFAULT
                        val channel = NotificationChannel("YOUR_CHANNEL_ID", name, importance).apply {
                            description = descriptionText
                        }
                        // Register the channel with the system
                        val notificationManager: NotificationManager =
                            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)
                    }

                    if (!isSignIn){
                        signInScreen.showScreen(onClickSave = {
                            isSignIn = true
                            signInScreen.isSignIn = true
                            dataManager.saveSignInData(signInScreen) }
                        )
                    } else {
                        val tasksGroups: MutableList<TasksGroup> = if (dataManager.getFile().exists()) {
                            dataManager.readTaskGroupsInformation()
                        } else {
                            mutableListOf()
                        }
                        val mainScreen = MainScreen(
                            tasksGroups,
                            dataManager = dataManager,
                            isDialogOpen = dataManager.readShowAddNewTaskGroupDialogFile(),
                            username = dataManager.readSignInDataFile().userName)

                        mainScreen.Run()
                    }
                }
            }
        }
    }
}

/*
The class below is the class that should be use the make changes in the UI
 */

//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Daily Notification"
//            val descriptionText = "Daily Notification Channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel("YOUR_CHANNEL_ID", name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        setContent {
//            TOTD_FinalTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MyApp()
//                }
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @Preview(showBackground = true, showSystemUi = true)
//    @Composable
//    fun Preview() {
////        SignInScreen().showScreen {}
//        MyApp()
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @Composable
//    private fun MyApp() {
//        val ui: AppUI
//        val context = LocalContext.current
//        val dataManager = DataManager(context)
//        val signInScreen = SignInScreen()
//
//        var isSignIn by remember {
//            mutableStateOf(true)
//        }
//
//        if (signInScreen.userName.isNotEmpty()) {
//            isSignIn = true
//        }
//
//        TOTD_FinalTheme {
//            if (!isSignIn) {
//                signInScreen.showScreen(onClickSave = { isSignIn = true })
//            } else {
//                if (!dataManager.getFile().exists()) {
//                    dataManager.saveTaskGroupsInformation(mutableListOf<TasksGroup>())
//                }
//
//                val testListOfTasksGroup = mutableListOf<TasksGroup>()
//                val testTasksGroup = TasksGroup("Test")
//                testTasksGroup.addNewTask("Test")
//                testTasksGroup.addNewTask("Test")
//                testTasksGroup.addNewTask("Test")
//                testListOfTasksGroup.add(testTasksGroup)
//                testListOfTasksGroup.add(TasksGroup("Test2"))
//                testListOfTasksGroup.add(TasksGroup("Test3"))
//                testListOfTasksGroup.add(TasksGroup("Test4"))
//                val mainScreen =
//                    MainScreen(
//                        testListOfTasksGroup,
//                        dataManager = dataManager,
//                        username = "Test",
//                        isDialogOpen = dataManager.readShowAddNewTaskGroupDialogFile(),
//                    )
//
//                LaunchedEffect(key1 = true) {
//                    NotificationScheduler
//                        .scheduleNotification(
//                            context,
//                            hour = 10,
//                            minute = 35
//                        )
//                }
//                mainScreen.Run()
//            }
//        }
//    }
//}