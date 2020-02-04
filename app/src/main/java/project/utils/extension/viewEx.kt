package project.utils.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager
import project.utils.widget.RtlViewPager

fun View.hide(isHide: Boolean) {
    visibility = if (isHide) GONE else VISIBLE
}

fun View.visibleAnim(isVisible: Boolean) {
    this.animate()
            .alpha(if (!isVisible) 0.0f else 1.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    visibility = if (!isVisible) GONE else VISIBLE
                }
            })
}


fun RtlViewPager.addOnPageChangeListener(listener :(position: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            listener.invoke(position)
        }
    })
}