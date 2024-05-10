class Task(taskDescription: String) {
    private var taskDescription = taskDescription;

    fun getTaskDescription(): String {
        return this.taskDescription
    }

    fun setTaskDescription(taskDescription: String){
        this.taskDescription = taskDescription
    }
}