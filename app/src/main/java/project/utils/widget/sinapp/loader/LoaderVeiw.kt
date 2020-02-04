package project.utils.widget.sinapp.loader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import ir.sinapp.sarnakh.R


class LoaderView : RelativeLayout {

    private lateinit var mRootView: View

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        mRootView = inflate(context, R.layout.progress_dialog, this)
    }


}