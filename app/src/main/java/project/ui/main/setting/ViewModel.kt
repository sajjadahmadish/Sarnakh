package project.ui.main.setting

import androidx.databinding.ObservableInt
import project.data.DataManager
import project.ui.base.BaseViewModel
import project.utils.rx.SchedulerProvider
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle


class SettingViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
     dataManager: DataManager, schedulerProvider: SchedulerProvider)
    : BaseViewModel<SettingNavigator>( dataManager, schedulerProvider) {

    //
    var country: String? = null
    val language = ObservableInt(-1)

    fun setLang(language: String) {
        if (language == "fa")
            this.language.set(0)
        else if (language == "en")
            this.language.set(1)
    }

}




