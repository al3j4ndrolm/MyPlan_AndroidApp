package com.bizarr.myPlanApp

import com.google.gson.Gson
import java.io.File
import android.content.Context
import android.util.Log

class DataManager(context: Context) {
    private val userDataFile: File = File(context.filesDir, "totd_data.json")
    private val signInDataFile: File = File(context.filesDir, "totd_signing_data.json")
    private val gson = Gson()
    private var json: String = ""

    fun saveInformation(data: MutableList<TasksGroup>): String {
        try {
            val dataHolder = Data(data)
            json = gson.toJson(dataHolder)
            userDataFile.writeText(json)
            Log.d("DataManager", "Data saved: $json") // Check the JSON string
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error saving data: ${e.message}"
        }
        return json
    }
    fun readString(): MutableList<TasksGroup> {
        if (!userDataFile.exists()) return mutableListOf()
        val jsonData = userDataFile.readText()
        Log.d("DataManager", "Data read: $jsonData") // Check the JSON string when read
        val dataHolder = gson.fromJson(jsonData, Data::class.java)
        return dataHolder.data.toMutableList()
    }

    fun getFile(): File {
        return this.userDataFile
    }
    fun saveSignInData(data: SignInScreen): String {
        try {
            val dataHolder = SignInData(data)
            json = gson.toJson(dataHolder)
            signInDataFile.writeText(json)
            Log.d("DataManager", "Data saved: $json") // Check the JSON string
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error saving data: ${e.message}"
        }
        return json
    }
    fun readSignInDataFile(): SignInScreen {

            if (!signInDataFile.exists()){
                return SignInScreen()
            }
            else {
                val jsonData = signInDataFile.readText()
                Log.d("DataManager", "Data read: $jsonData") // Check the JSON string when read
                val dataHolder = gson.fromJson(jsonData, SignInData::class.java)
                return dataHolder.signInData
            }

//            val jsonData = signInDataFile.readText()
//            Log.d("DataManager", "Data read: $jsonData") // Check the JSON string when read
//            val dataHolder = gson.fromJson(jsonData, SignInData::class.java)
//            return dataHolder.signInData.userName

    }
}
