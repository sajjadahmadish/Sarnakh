package project.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import project.ui.base.BaseActivity


inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

fun BaseActivity<*, *>.isRtl(): Boolean {
    return currentLanguage.language == "fa"
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)