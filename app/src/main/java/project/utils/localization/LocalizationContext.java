package project.utils.localization;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


public class LocalizationContext extends ContextWrapper {
    public LocalizationContext(Context base) {
        super(base);
    }

    @Override
    public Resources getResources() {
        Configuration conf = super.getResources().getConfiguration();
        conf.locale = LanguageSetting.getLanguage(this);
        DisplayMetrics metrics = super.getResources().getDisplayMetrics();
        return new Resources(getAssets(), metrics, conf);
    }
}
