package project.ui.map

import androidx.databinding.ObservableField
import com.google.android.gms.maps.model.Marker
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.AppLogger
import project.utils.extension.forDatabase
import project.utils.extension.forIo
import project.utils.extension.onUi
import project.utils.rx.SchedulerProvider

class MapViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MapNavigator>(dataManager, schedulerProvider) {


    fun getMarkers(callback: (List<project.data.model.Marker>) -> Unit) {
        this += dataManager.markers
            .forDatabase(schedulerProvider)
            .onUi(schedulerProvider)
            .subscribe {
                callback.invoke(it)
            }
    }

    init {
        onlineMarkers()
    }

    private fun onlineMarkers() {
        withInternet {
            this += dataManager.markers()
                .forIo(schedulerProvider)
                .onUi(schedulerProvider)
                .subscribe {
                    insert(it.markers)
                }
            true
        }
    }

    private fun insert(it: List<project.data.model.Marker>) {
        this +=  dataManager.insertMarker(it)
            .forDatabase(schedulerProvider)
            .onUi(schedulerProvider)
            .subscribe {
                AppLogger.i("insert markers")
            }
    }


    val selected = ObservableField<project.data.model.Marker>()


}




