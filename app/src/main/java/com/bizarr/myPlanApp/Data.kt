package com.bizarr.myPlanApp

data class Data(
    val data: List<TasksGroup>

)
data class SignInData(
    val signInData: SignInScreen
)

data class ShowAddNewTaskGroupDialogData(
    var isAddNewTaskGroupDialogOpenData: Boolean
)

data class ShowAddNewTaskDialogData(
    var isAddNewTaskDialogOpenData: Boolean
)