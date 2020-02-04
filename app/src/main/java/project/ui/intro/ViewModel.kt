package project.ui.intro

import androidx.databinding.ObservableInt
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class IntroViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IntroNavigator>(dataManager, schedulerProvider) {

    val maxStep = 4

    val tab = ObservableInt(0)


    fun doNotShowAgain() {
//        dataManager.showIntro = true
    }



}




