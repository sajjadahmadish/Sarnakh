package project.ui.missionList

import ir.sinapp.sarnakh.R
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class MissionListViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<MissionListNavigator>(dataManager, schedulerProvider) {


    val dotCoors = Array(5) { IntArray(2) }
    val pics =
        intArrayOf(R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5)
    val maps = intArrayOf(
        R.drawable.map_paris,
        R.drawable.map_seoul,
        R.drawable.map_london,
        R.drawable.map_beijing,
        R.drawable.map_greece
    )
    val descriptions =
        intArrayOf(R.string.text1, R.string.text2, R.string.text3, R.string.text4, R.string.text5)
    //    private final String[] countries = {"PARIS", "SEOUL", "LONDON", "BEIJING", "THIRA"};
    val countries =
        arrayOf("پاریس", "سوول", "لندن", "بیژینگ", "ثیرا")
    //    private final String[] places = {"The Louvre", "Gwanghwamun", "Tower Bridge", "Temple of Heaven", "Aegeana Sea"};
    val places =
        arrayOf("لوور", "گوانگوامون", "برج پل", "معبد بهشت", "دریای آجینا")
    val temperatures =
        arrayOf("21°C", "19°C", "17°C", "23°C", "20°C")
    //private final String[] times = {"Aug 1 - Dec 15    7:00-18:00", "Sep 5 - Nov 10    8:00-16:00", "Mar 8 - May 21    7:00-18:00"};
    val times = arrayOf(
        "۱ اردیبهشت - ۱۵ خرداد  ۷:۰۰-۱۸:۰۰",
        "۵ تیر - ۱۰ مرداد  ۸:۰۰-۱۶:۰۰",
        "۸بهمن - ۲۱ اسفند  ۷:۰۰-۱۸:۰۰"
    )
}




