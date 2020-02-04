package project.utils.cardslider

import android.graphics.Bitmap
import android.util.LruCache

/**
 * LruCache for caching background bitmaps for [DecodeBitmapTask].
 */
internal class BackgroundBitmapCache {
    private var mBackgroundsCache: LruCache<Int?, Bitmap>? = null
    private fun init() {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 5
        mBackgroundsCache = object : LruCache<Int?, Bitmap>(cacheSize) {
            override fun sizeOf(
                key: Int?,
                bitmap: Bitmap
            ): Int { // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun addBitmapToBgMemoryCache(key: Int?, bitmap: Bitmap) {
        if (getBitmapFromBgMemCache(key) == null) {
            mBackgroundsCache!!.put(key, bitmap)
        }
    }

    fun getBitmapFromBgMemCache(key: Int?): Bitmap? {
        return mBackgroundsCache!![key]
    }

    companion object {
        private var instance: BackgroundBitmapCache? = null
        fun getInstance(): BackgroundBitmapCache? {
            if (instance == null) {
                instance = BackgroundBitmapCache()
                instance!!.init()
            }
            return instance
        }
    }
}