package project.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.LocaleList
import android.provider.Settings
import android.util.Patterns
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import ir.sinapp.sarnakh.R
import java.io.IOException
import java.util.*


object CommonUtils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()

        return String(buffer, charset("UTF-8"))
    }

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val view = View.inflate(context, R.layout.progress_dialog, null)

        progressDialog.setContentView(view)

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }


    fun updateLanguage(locale: Locale, activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val resources = activity.resources
            val config = resources.configuration
            val ll = LocaleList(locale)
            config.setLocales(ll)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }


    fun showDialog(
        activity: AppCompatActivity,
        @StringRes title: Int,
        @StringRes msg: Int,
        @ColorRes colorId: Int,
        @DrawableRes iconId: Int,
        gravity: Flashbar.Gravity = Flashbar.Gravity.TOP
    ) {
        Flashbar.Builder(activity)
            .gravity(gravity)
            .title(activity.getString(title))
            .message(activity.getString(msg))
            .backgroundColorRes(colorId)
            .dismissOnTapOutside()
            .icon(iconId)
            .showIcon()
            .enableSwipeToDismiss()
            .duration(3000)
            .enterAnimation(
                FlashAnim.with(activity)
                    .animateBar()
                    .duration(350)
                    .alpha()
                    .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(activity)
                    .animateBar()
                    .duration(300)
                    .accelerateDecelerate()
            )
            .build().show()
    }


    fun typefaceFromAsset(str: String, context: Context): Typeface? {
        return Typeface.createFromAsset(context.resources.assets, str)
    }


}// This utility class is not publicly instantiable

