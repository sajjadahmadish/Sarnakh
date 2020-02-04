package project.utils.localization;

import android.app.Application;
import android.content.Context;

public class LocalizationApplicationDelegate {
    private Application application;

    public LocalizationApplicationDelegate(Application application) {
        this.application = application;
    }

    public void onConfigurationChanged(Context context) {
        LocalizationUtility.applyLocalizationContext(context);
    }

    public Context attachBaseContext(Context context) {
        return LocalizationUtility.applyLocalizationContext(context);
    }

    public Context getApplicationContext(Context applicationContext) {
        return LocalizationUtility.applyLocalizationContext(applicationContext);
    }

//    public static void onConfigurationChanged(Context context) {
//        updateLocaleConfiguration(context);
//    }

//    public static Context updateLocaleConfiguration(Context baseContext) {
//        return LocalizationUtility.applyLocalizationContext(baseContext);
//    }

//    public static Context attachBaseContext(Context context) {
//        return updateLocaleConfiguration(context);
//    }
}
