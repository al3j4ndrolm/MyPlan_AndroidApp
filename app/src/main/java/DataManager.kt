import com.google.gson.Gson
import java.io.File
import android.content.Context
import android.util.Log

class DataManager(context: Context) {
    private val file: File = File(context.filesDir, "totddata.json")
    private val gson = Gson()
    private var json: String = ""

    fun saveInformation(data: MutableList<TasksGroup>): String {
        try {
            val dataHolder = Data(data)
            json = gson.toJson(dataHolder)
            file.writeText(json)
            Log.d("DataManager", "Data saved: $json") // Check the JSON string
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error saving data: ${e.message}"
        }
        return json
    }

    fun readString(): MutableList<TasksGroup> {
        if (!file.exists()) return mutableListOf()
        val jsonData = file.readText()
        Log.d("DataManager", "Data read: $jsonData") // Check the JSON string when read
        val dataHolder = gson.fromJson(jsonData, Data::class.java)
        return dataHolder.data.toMutableList()
    }

    fun getFile(): File {
        return this.file
    }
}
