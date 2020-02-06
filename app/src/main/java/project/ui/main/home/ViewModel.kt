package project.ui.main.home

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {

    //
    val list = listOf(
        "http://s7.picofile.com/file/8387312218/kish_offer.png",
        "http://s7.picofile.com/file/8387312142/wrold_offer.png",
        "https://www.salehantravel.com/uploadfiles/05480cb180694ee28162ad4374b825d5.jpg",
        "https://www.salehantravel.com/uploadfiles/9ba8e256f2be4cb193e0a04cec7255e1.jpg"
    )

}




