package project.ui.main.home

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {

    //
    val list = listOf(
        "https://safirancp.faranegar.com/uploads/d8be0164-7b62-4e7c-9f6f-98d3c39acd3f//06.jpg",
        "https://bisanseir.com/dist/admin/images/main_slide/org/main_slide_0.jpg",
        "https://www.salehantravel.com/uploadfiles/05480cb180694ee28162ad4374b825d5.jpg",
        "https://www.salehantravel.com/uploadfiles/9ba8e256f2be4cb193e0a04cec7255e1.jpg"
    )

}




