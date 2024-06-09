package com.example.totd_final

import DataManager
import MainScreen
import SignInScreen
import TasksGroup
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.totd_final.ui.theme.TOTD_FinalTheme

/*
The class below is the class that runs the code and work in the device
 */

//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            TOTD_FinalTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val dataManager = DataManager(this)
//                    val signInScreen = SignInScreen()
//
//                    var isSignIn by remember {
//                        mutableStateOf(dataManager.readSignInDataFile().isSignIn)
//                    }
//
//                    if (!isSignIn){
//                        signInScreen.showScreen(onClickSave = {
//                            isSignIn = true
//                            signInScreen.isSignIn = true
//                            dataManager.saveSignInData(signInScreen) }
//                        )
//                    } else {
//                        val tasksGroups: MutableList<TasksGroup> = if (dataManager.getFile().exists()) {
//                            dataManager.readString()
//                        } else {
//                            mutableListOf()
//                        }
//                        val mainScreen = MainScreen(
//                            tasksGroups,
//                            dataManager = dataManager,
//                            username = dataManager.readSignInDataFile().userName)
//                        mainScreen.Run()
//                    }
//                }
//            }
//        }
//    }
//}

/*
The class below is the class that should be use the make changes in the UI
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
                    MyApp()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        MyApp()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun MyApp() {
        val context = LocalContext.current
        val dataManager = DataManager(context)
        val signInScreen = SignInScreen()

        var isSignIn by remember {
            mutableStateOf(true)
        }

        if (signInScreen.userName.isNotEmpty()){
            isSignIn = true
        }

        TOTD_FinalTheme {
            if (!isSignIn){
                signInScreen.showScreen(onClickSave = {isSignIn = true})
            } else {
                if (!dataManager.getFile().exists()){
                    dataManager.saveInformation(mutableListOf<TasksGroup>())
                }
                val testListOfTasksGroup = mutableListOf<TasksGroup>()
//                testListOfTasksGroup.add(TasksGroup("Test"))
//                testListOfTasksGroup.add(TasksGroup("Test2"))
//                testListOfTasksGroup.add(TasksGroup("Test3"))
//                testListOfTasksGroup.add(TasksGroup("Test4"))
//                testListOfTasksGroup.add(TasksGroup("Test5"))
//                testListOfTasksGroup.add(TasksGroup("Test6"))
//                testListOfTasksGroup.add(TasksGroup("Test7"))
                val mainScreen = MainScreen(testListOfTasksGroup, dataManager = dataManager, username = "Test")
                mainScreen.Run()
            }
        }
    }
}