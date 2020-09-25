package project.ui.main.help

import androidx.lifecycle.SavedStateHandle
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject


class HelpViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
     dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<HelpNavigator>( dataManager, schedulerProvider) {

    //

}




