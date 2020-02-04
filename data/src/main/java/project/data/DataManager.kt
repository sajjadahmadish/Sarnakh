package project.data


import io.reactivex.Observable
import project.data.local.db.DbHelper
import project.data.local.preferences.PreferenceHelper
import project.data.remote.SimpleApi


interface DataManager : DbHelper, PreferenceHelper, SimpleApi {

//    Observable<List<QuestionCardData>> getQuestionCardData();
//
//    Observable<Boolean> seedDatabaseOptions();
//
//    Observable<Boolean> seedDatabaseQuestions();
//

    fun setUserAsLoggedOut()

    fun updateApiHeader(userId: String?, token: String?)

    fun updateUserInfo(userId: String?, token: String, loggedInMode: LoggedInMode)

    fun updateBriefInfo(firstName: String, lastName: String, gender: Boolean)

    enum class LoggedInMode {
        LOGGED_OUT,
        LOGGED_IN
    }

}