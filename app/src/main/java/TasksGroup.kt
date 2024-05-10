class TasksGroup(taskGroupName: String) {
    var taskGroupName: String = taskGroupName
    var numberOfTasks = 0
    var taskList = mutableListOf<Task>()

    fun setNewTaskGroupName(newName: String){
        this.taskGroupName = newName
    }

    fun addNewTask(task: String){
        taskList.add(Task(task))
        this.numberOfTasks++
    }

    fun removeTask(task: Task){
        taskList.remove(task)
        this.numberOfTasks--
    }

    fun clearTaskGroup(){
        taskList.clear()
        this.numberOfTasks = 0
    }

    fun getLenght(): Int {
        return numberOfTasks
    }
}