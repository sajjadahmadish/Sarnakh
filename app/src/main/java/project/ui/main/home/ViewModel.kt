package project.ui.main.home

import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {

    //
    val list = listOf(
        "https://workshop.hostiran.net/file/2018/07/01-2.png",
        "https://banner-design.ir/wp-content/uploads/POST.jpg",
        "https://banner-design.ir/wp-content/uploads/nowruz-post-offer.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8dVYL-1SYWM9-XC-z0bEso6zEhqVVM0_1DZmHHuuTVWiy7Bmk",
        "https://banner-design.ir/wp-content/uploads/eid-post-blog-fetr96.jpg)"
    )

}




