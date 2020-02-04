package project.utils

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.facebook.drawee.view.SimpleDraweeView
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding3.view.visibility
import io.reactivex.android.schedulers.AndroidSchedulers

import project.ui.base.BaseViewModel
import project.utils.extension.onUi
import project.utils.extension.visibleAnim
import project.utils.widget.toolbar.Px
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


@BindingAdapter("android:layout_margin")
fun setBottomMargin(view: View, bottomMargin: Float) {

    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(
        bottomMargin.roundToInt(), layoutParams.topMargin,
        bottomMargin.roundToInt(), bottomMargin.roundToInt()
    )
    view.layoutParams = layoutParams
}


@BindingAdapter("typeFace")
fun setTypeFace(nts: NavigationTabStrip, typeFace: String) {
    val font = Typeface.createFromAsset(nts.resources.assets, typeFace)
    nts.typeface = font
}

@BindingAdapter("typeFace")
fun setTypeFace(txt: TextView, typeFace: String) {
    val font = Typeface.createFromAsset(txt.resources.assets, typeFace)
    txt.typeface = font
}

@BindingAdapter("srcFab")
fun srcFab(fab: FloatingActionButton, drawableRes: Drawable) {
    fab.setImageDrawable(drawableRes)
}


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: SimpleDraweeView, url: String) {
    val uri = Uri.parse("http://classmate.ahmadidev.ir/res/image/class/$url.png")
    imageView.setImageURI(uri, imageView.context)
}


@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: String?) {
    if (imageUri == null) {
        view.setImageURI(null)
    } else {
        Glide.with(view).load(Uri.parse(imageUri)).into(view)
    }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: Uri) {
    Glide.with(view).load(imageUri).into(view)
}

@BindingAdapter("android:src")
fun setImageDrawable(view: ImageView, drawable: Drawable) {
    Glide.with(view).load(drawable).into(view)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    Glide.with(imageView).load(resource).into(imageView)
}

@BindingAdapter("srcClazz")
fun setImage(imageView: ImageView, str: String) {
    val drawable = Tools.getDrawable("p$str", imageView.context)
    Glide.with(imageView).load(drawable).into(imageView)
}

@BindingAdapter("android:rotation")
fun setRotation(imageView: ImageButton, float: Float) {
    imageView.rotation = float
}

@BindingAdapter("layout_vertical")
fun bindLayoutManager(recyclerView: RecyclerView, vertical: Boolean) {
    val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, orientation, false)
}

@BindingAdapter("layout_grid")
fun bindLayoutManager(recyclerView: RecyclerView, spanCount: Int) {
    recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanCount)
    recyclerView.setHasFixedSize(true)
}

//@BindingAdapter("adapter")
//fun bindAdapter(recyclerView: RecyclerView, adapter: AddressAdapter) {
//    recyclerView.adapter = adapter
//}

var c = 0

@BindingAdapter("animateVisible")
fun bindAnimateVisible(view: View, flag: Boolean) {
    c++
    val a = c
    if (!flag) {
        if (view.visibility == View.VISIBLE)
            io.reactivex.Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (c == a)
                        ViewAnimation.flyOutDown(view) {
                            view.visibility = View.GONE
                        }
                }
    } else {
        if (view.visibility == View.GONE)
            ViewAnimation.flyInUp(view) {}
    }
}



@BindingAdapter("visible")
fun bindVisible(view: View, flag: Boolean) {
    view.visibility().accept(flag)
}



@BindingAdapter("state")
fun bindState(lottie: LottieAnimationView, state: BaseViewModel.NetStatus) {
    val layoutParams = lottie.layoutParams as MarginLayoutParams
    var margin = 0
    lottie.setAnimation("success.json")
    when (state) {
        BaseViewModel.NetStatus.CONNECTED -> {
            lottie.repeatCount = 0
            lottie.setMinAndMaxProgress(0.567f, 1f)
        }
        BaseViewModel.NetStatus.CONNECTING -> {
            lottie.repeatCount = LottieDrawable.INFINITE
            lottie.setMinAndMaxProgress(0f, 0.57f)
        }
        else -> {
            lottie.setAnimation("connection.json")
            lottie.repeatCount = LottieDrawable.INFINITE
            margin = 5f.dp().roundToInt()
        }
    }
    layoutParams.setMargins(
        layoutParams.leftMargin, margin,
        layoutParams.rightMargin, margin
    )
    lottie.playAnimation()
}


@BindingAdapter("asyncText")
fun setTextAsync(textView: AppCompatTextView, text: String) {
    val params = TextViewCompat.getTextMetricsParams(textView)
    textView.setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}


@BindingAdapter("fontPath")
fun setFontPath(textView: AppCompatTextView, font: String) {
    val typeface =
        CommonUtils.typefaceFromAsset(font, textView.context)
    textView.typeface = typeface
}


fun BaseObservable.onChange(callback: (sender: Observable?, propertyId: Int) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            callback.invoke(sender, propertyId)
        }
    })
}

fun <T> ObservableField<T>.change(callback: (T?) -> Boolean) {
    this.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            val temp = callback.invoke(this@change.get())
            if (temp) removeOnPropertyChangedCallback(this)
        }
    })
}