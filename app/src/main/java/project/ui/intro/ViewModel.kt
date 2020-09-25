package project.ui.intro

import androidx.databinding.ObservableInt
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class IntroViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<IntroNavigator>(dataManager, schedulerProvider) {

    val maxStep = 4

    val tab = ObservableInt(0)


    fun doNotShowAgain() {
//        dataManager.showIntro = true
    }



}




