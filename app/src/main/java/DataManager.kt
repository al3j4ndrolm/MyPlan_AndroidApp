import com.google.gson.Gson
import java.io.File
import android.content.Context

class DataManager(context: Context) {
    private val file: File = File(context.filesDir, "appdata.json")
    private val gson = Gson()
    private var json: String = ""
    private var initialList = listOf(TasksGroup("Hello world", "1"))

    fun saveInformation(data: List<TasksGroup>): String {
        try {
            val dataHolder = Test(data)
            json = gson.toJson(dataHolder)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error saving data: ${e.message}"
        }
        return json
    }

    fun readString(): List<TasksGroup> {

        val jsonData = file.readText()
        val dataHolder = gson.fromJson(jsonData, Test::class.java)
        return dataHolder.data
    }
}
