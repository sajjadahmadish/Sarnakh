package project.ui.main.best

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class BestViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<BestNavigator>(dataManager, schedulerProvider) {

    //

}




