import com.google.gson.Gson
import java.io.File
import android.content.Context

open class DataManager(context: Context) {
    private val file: File = File(context.filesDir, "totddata.json")
    private val gson = Gson()
    private var json: String = ""


    open fun saveInformation(data: MutableList<TasksGroup>): String {
        try {
            val dataHolder = Data(data)
            json = gson.toJson(dataHolder)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error saving data: ${e.message}"
        }
        return json
    }

    fun readString(): MutableList<TasksGroup> {
        val jsonData = file.readText()
        val dataHolder = gson.fromJson(jsonData, Data::class.java)
        return dataHolder.data.toMutableList()
    }

    fun getFile(): File {
        return this.file
    }
}
