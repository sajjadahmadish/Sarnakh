package project.utils.cardslider

import android.content.res.Resources
import android.graphics.*
import android.os.AsyncTask
import androidx.annotation.DrawableRes
import ir.sinapp.sarnakh.R
import java.lang.ref.Reference
import java.lang.ref.WeakReference

class DecodeBitmapTask(
    private val resources: Resources, @DrawableRes private val bitmapResId: Int,
    private val reqWidth: Int, private val reqHeight: Int,
    listener: Listener
) : AsyncTask<Void?, Void?, Bitmap?>() {
    private val cache: BackgroundBitmapCache = BackgroundBitmapCache.getInstance()
    private val refListener: Reference<Listener>

    interface Listener {
        fun onPostExecuted(bitmap: Bitmap?)
    }

    override fun doInBackground(vararg voids: Void?): Bitmap? {
        val cachedBitmap = cache.getBitmapFromBgMemCache(bitmapResId)
        if (cachedBitmap != null) {
            return cachedBitmap
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, bitmapResId, options)
        val width = options.outWidth
        val height = options.outHeight
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfWidth = width / 2
            val halfHeight = height / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth && !isCancelled
            ) {
                inSampleSize *= 2
            }
        }
        if (isCancelled) {
            return null
        }
        options.inSampleSize = inSampleSize
        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val decodedBitmap = BitmapFactory.decodeResource(resources, bitmapResId, options)
        val result: Bitmap
        result = getRoundedCornerBitmap(
            decodedBitmap,
            resources.getDimension(R.dimen.card_corner_radius), reqWidth, reqHeight
        )
        decodedBitmap.recycle()

        cache.addBitmapToBgMemoryCache(bitmapResId, result)
        return result
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        val listener = refListener.get()
        listener?.onPostExecuted(bitmap)
    }

    companion object {
        fun getRoundedCornerBitmap(
            bitmap: Bitmap,
            pixels: Float,
            width: Int,
            height: Int
        ): Bitmap {
            val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)
            val sourceWidth = bitmap.width
            val sourceHeight = bitmap.height
            val xScale = width.toFloat() / bitmap.width
            val yScale = height.toFloat() / bitmap.height
            val scale = xScale.coerceAtLeast(yScale)
            val scaledWidth = scale * sourceWidth
            val scaledHeight = scale * sourceHeight
            val left = (width - scaledWidth) / 2
            val top = (height - scaledHeight) / 2
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, width, height)
            val rectF = RectF(rect)
            val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, pixels, pixels, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, null, targetRect, paint)
            return output
        }
    }

    init {
        refListener =
            WeakReference(listener)
    }
}