package project.ui.ticket

import android.view.View
import androidx.databinding.ObservableBoolean
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class TicketViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<TicketNavigator>(dataManager, schedulerProvider) {

    //

    fun onClickBack(view: View) = navigator.goBack()



}




