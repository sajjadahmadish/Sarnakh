package project.ui.main.notification

import androidx.lifecycle.SavedStateHandle
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject


class NotificationViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
     dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<NotificationNavigator>( dataManager, schedulerProvider) {

    //

}




