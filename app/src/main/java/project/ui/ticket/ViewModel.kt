package project.ui.ticket

import android.view.View
import androidx.databinding.ObservableBoolean
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class TicketViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<TicketNavigator>(dataManager, schedulerProvider) {

    //

    fun onClickBack(view: View) = navigator.goBack()



}




