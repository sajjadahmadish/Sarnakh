package project.data.local.preferences

import project.data.DataManager

interface PreferenceHelper {

    var showIntro: Boolean

    var local: String

    var currentUserId: String?

    var accessToken: String?

    var isRegister: Boolean?

    var userLoggedInMode: DataManager.LoggedInMode

    var userFirstName: String?

    var userLastName: String?

    var gender: Boolean?

    var notify: Boolean

    var theme: DataManager.Theme

}