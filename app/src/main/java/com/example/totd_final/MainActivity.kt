package com.example.totd_final

import DataManager
import MainScreen
import TasksGroup
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
//                    // Check and load existing data
//                    val tasksGroups: MutableList<TasksGroup> = if (dataManager.getFile().exists()) {
//                        dataManager.readString()
//                    } else {
//                        mutableListOf()
//                    }
//
//                    val mainScreen = MainScreen(tasksGroups, dataManager = dataManager)
//                    mainScreen.Run()
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

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        TOTD_FinalTheme {
            MyApp()
        }
    }

    @Composable
    private fun MyApp() {
        val context = LocalContext.current
        val dataManager = DataManager(context)

        if (!dataManager.getFile().exists()){
            dataManager.saveInformation(mutableListOf<TasksGroup>())
        }

//        var tasksGroup = TasksGroup("MATH", "1A")
//        tasksGroup.addNewTask("This is a test")
        val testListOfTasksGroup = mutableListOf<TasksGroup>()
//        testListOfTasksGroup.add(tasksGroup)

        val mainScreen = MainScreen(testListOfTasksGroup, dataManager = dataManager)
        mainScreen.Run()
    }
}