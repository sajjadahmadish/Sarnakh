package project.ui.login

interface LoginNavigator {

    fun openMain()
    fun handleError(throwable: Throwable)
    fun handleErrorApi(response: Int,  message:String)
}