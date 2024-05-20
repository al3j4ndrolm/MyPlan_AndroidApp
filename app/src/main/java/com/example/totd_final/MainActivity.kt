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
import androidx.compose.ui.tooling.preview.Preview
import com.example.totd_final.ui.theme.TOTD_FinalTheme

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

                    if (!dataManager.getFile().exists()){
                        dataManager.saveInformation(mutableListOf<TasksGroup>())
                    }

                    val mainScreen = MainScreen(dataManager.readString(), dataManager = dataManager)
                    mainScreen.Run()
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {

    TOTD_FinalTheme {
    }
}