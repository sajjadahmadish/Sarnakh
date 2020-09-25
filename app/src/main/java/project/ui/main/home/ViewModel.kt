package project.ui.main.home

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class HomeViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {

    //
    val list = listOf(
        "http://s6.picofile.com/file/8387319034/kish_offer_4x.png",
        "http://s6.picofile.com/file/8387319076/wrold_offer_4x.png",
        "https://www.salehantravel.com/uploadfiles/05480cb180694ee28162ad4374b825d5.jpg",
        "https://www.salehantravel.com/uploadfiles/9ba8e256f2be4cb193e0a04cec7255e1.jpg"
    )

}




