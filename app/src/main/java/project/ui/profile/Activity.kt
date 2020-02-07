package project.ui.profile

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import cn.nekocode.badge.BadgeDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityProfileBinding


class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(ActivityProfileBinding::class.java),
    ProfileNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        initToolbar()

        val bDrawable = BadgeDrawable.Builder()
            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
            .text1(" XP ")
            .textColor(Color.parseColor("#ff8533"))
            .build()
        binding.xpBadgeText.setImageDrawable(bDrawable)

        // display image
        displayImageOriginal(
            binding.image1,
            R.drawable.image_18
        )
        displayImageOriginal(
            binding.image2,
            R.drawable.image_30
        )
        displayImageOriginal(
            binding.image3,
            R.drawable.image_21
        )
        displayImageOriginal(
            binding.image4,
            R.drawable.image_23
        )
        displayImageOriginal(
            binding.image5,
            R.drawable.image_19
        )
        displayImageOriginal(
            binding.image6,
            R.drawable.image_11
        )
    }

    private fun displayImageOriginal(
        img: ImageView,
        @DrawableRes drawable: Int
    ) {
        try {
            Glide.with(this).load(drawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img)
        } catch (e: Exception) {
        }
    }

    private fun initToolbar() {
        val toolbar =
            findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setSystemBarColor(R.color.green_500)
    }

    private fun setSystemBarColor(@ColorRes color: Int) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

}




