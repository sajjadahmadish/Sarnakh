package project.ui.missionList.cardhelper

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.ramotion.cardslider.DefaultViewUpdater

class CardsUpdater : DefaultViewUpdater() {
    override fun updateView(view: View, position: Float) {
        super.updateView(view, position)
        val card = view as CardView
        val alphaView = card.getChildAt(1)
        val imageView = card.getChildAt(0)
        if (position < 0) {
            val alpha = ViewCompat.getAlpha(view)
            ViewCompat.setAlpha(view, 1f)
            ViewCompat.setAlpha(alphaView, 0.9f - alpha)
            ViewCompat.setAlpha(imageView, 0.3f + alpha)
        } else {
            ViewCompat.setAlpha(alphaView, 0f)
            ViewCompat.setAlpha(imageView, 1f)
        }
        val lm = layoutManager
        val ratio =
            lm.getDecoratedLeft(view).toFloat() / lm.activeCardLeft
        val z: Float
        z = when {
            position < 0 -> {
                Z_CENTER_1 * ratio
            }
            position < 0.5f -> {
                Z_CENTER_1.toFloat()
            }
            position < 1f -> {
                Z_CENTER_2.toFloat()
            }
            else -> {
                Z_RIGHT.toFloat()
            }
        }
        card.cardElevation = 0f.coerceAtLeast(z)
    }
}