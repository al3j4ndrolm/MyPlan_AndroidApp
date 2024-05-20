import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class TaskGroupLists(val groupLists: MutableList<TasksGroup>) {

    private val tasksGroupList = groupLists
    private var incompleteTasks = 0
    fun getNumberOfIncompleteTasks(): Int {
        var numberIncompleteTasks = 0

       for (taskGroup in tasksGroupList){
           for (task in taskGroup.taskList){
               if (!task.getState()){
                   numberIncompleteTasks++
               }
           }
       }
        return numberIncompleteTasks
    }

    fun getIncompletedTasks(): Int {
        return this.incompleteTasks
    }
}