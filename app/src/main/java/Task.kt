class Task(taskDescription: String) {
    private var taskDescription = taskDescription;
    private var check = false

    fun getTaskDescription(): String {
        return this.taskDescription
    }

    fun setTaskDescription(taskDescription: String){
        this.taskDescription = taskDescription
    }

    fun getState(): Boolean {
        return this.check
    }

    fun setState(boolean: Boolean): Boolean{
        this.check = boolean
        return boolean
    }
}