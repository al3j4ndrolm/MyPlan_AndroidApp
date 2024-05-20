import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

data class TasksGroup(var taskGroupName: String, var taskGroupNumber: String) {
    var taskList = mutableStateListOf<Task>()


    fun setNewTaskGroupName(newName: String){
        this.taskGroupName = newName
    }

    fun addNewTask(task: String){
        taskList.add(Task(task))
    }

    fun removeTask(task: Task){
        taskList.remove(task)

    }

    fun clearTaskGroup(){
        taskList.clear()

    }

}