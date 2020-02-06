package project.ui.mission

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class MissionViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<MissionNavigator>(dataManager, schedulerProvider) {

    val list = listOf(
        "https://delta.ir/mag/wp-content/uploads/2019/05/%DA%AF%D9%84%D8%B3%D8%AA%D8%A7%D9%86.jpg",
        "https://www.snapptrip.com/blog/wp-content/uploads/2018/12/%DA%A9%D8%A7%D8%AE-%DA%AF%D9%84%D8%B3%D8%AA%D8%A7%D9%86-650x406.jpg",
        "https://images.kojaro.com/2016/6/12999f45-790d-4a70-8333-740ea8c30f35.jpg",
        "https://mashrooh.com/images/content/tourism/1395/22/Golestan-Palace.jpg"
    )

}




