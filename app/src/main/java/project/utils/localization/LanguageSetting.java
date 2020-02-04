package project.utils.localization;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;


class LanguageSetting {
    private static final String PREFERENCE_LANGUAGE = "pref_language";
    private static final String KEY_LANGUAGE = "key_language";
    private static Locale DEFAULT_LANGUAGE = Locale.ENGLISH;

    static Locale getDefaultLanguage() {
        return DEFAULT_LANGUAGE;
    }

    static void setDefaultLanguage(Locale locale) {
        DEFAULT_LANGUAGE = locale;
    }

    static void setLanguage(Context context, Locale locale) {
        Locale.setDefault(locale);
        SharedPreferences.Editor editor = getLanguagePreference(context).edit();
        editor.putString(KEY_LANGUAGE, locale.toString());
        editor.apply();
    }

    static Locale getLanguage(Context context) {
        String[] language = getLanguagePreference(context)
                .getString(KEY_LANGUAGE, DEFAULT_LANGUAGE.toString())
                .split("_");
        Locale locale;
        if (language.length == 1) {
            locale = new Locale(language[0]);
        } else if (language.length == 2) {
            locale = new Locale(language[0], language[1].toUpperCase());
        } else {
            locale = DEFAULT_LANGUAGE;
        }
        return locale;
    }

    private static SharedPreferences getLanguagePreference(Context context) {
        return context.getSharedPreferences(PREFERENCE_LANGUAGE, Activity.MODE_PRIVATE);
    }
}
