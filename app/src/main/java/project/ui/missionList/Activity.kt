package project.ui.missionList

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ramotion.cardslider.CardSliderLayoutManager
import com.ramotion.cardslider.CardSnapHelper
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityMissionListBinding
import project.ui.base.BaseActivity
import project.ui.missionList.cardhelper.SliderAdapter
import project.utils.CommonUtils
import project.utils.cardslider.DecodeBitmapTask
import java.util.*
import javax.inject.Inject


class MissionListActivity : BaseActivity<ActivityMissionListBinding, MissionListViewModel>(ActivityMissionListBinding::class.java), MissionListNavigator {
    //TODO: Add data binding
    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MissionListViewModel
    private lateinit var countries: Array<String>
    private lateinit var places: Array<String>
    private lateinit var times: Array<String>

    private lateinit var sliderAdapter: SliderAdapter

    private var layoutManger: CardSliderLayoutManager? = null
    private var countryOffset1 = 0
    private var countryOffset2 = 0
    private var countryAnimDuration: Long = 0
    private var currentPosition = 0

    private var decodeMapBitmapTask: DecodeBitmapTask? = null
    private var mapLoadListener: DecodeBitmapTask.Listener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        sliderAdapter = SliderAdapter(
            viewModel.pics,
            20,
            OnCardClickListener()
        )

        initArrays()
        initRecyclerView()
        initCountryText()
        initSwitchers()
        initGreenDot()
    }

    private fun initArrays(){
        times = resources.getStringArray(R.array.mission_list_date)
        places = resources.getStringArray(R.array.mission_list_places)
        countries = resources.getStringArray(R.array.mission_list_countries)
    }

    private fun initRecyclerView() {

        binding.recyclerView.adapter = sliderAdapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange()
                }
            }
        })
        layoutManger = binding.recyclerView.layoutManager as CardSliderLayoutManager?
        CardSnapHelper().attachToRecyclerView(binding.recyclerView)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing && decodeMapBitmapTask != null) {
            decodeMapBitmapTask!!.cancel(true)
        }
    }

    private fun initSwitchers(){

        binding.tsTemperature.setFactory(
            TextViewFactory(
                R.style.TemperatureTextView,
                true
            )
        )
        binding.tsTemperature.setCurrentText(viewModel.temperatures[0])

        binding.tsPlace.setFactory(
            TextViewFactory(
                R.style.PlaceTextView,
                false
            )
        )
        binding.tsPlace.setCurrentText(places[0])

        binding.tsClock.setFactory(
            TextViewFactory(
                R.style.ClockTextView,
                false
            )
        )
        binding.tsClock.setCurrentText(times[0])

        binding.tsDescription.setInAnimation(this, android.R.anim.fade_in)
        binding.tsDescription.setOutAnimation(this, android.R.anim.fade_out)
        binding.tsDescription.setFactory(
            TextViewFactory(
                R.style.DescriptionTextView,
                false
            )
        )
        binding.tsDescription.setCurrentText(getString(viewModel.descriptions[0]))
        binding.tsMap.setInAnimation(this, R.anim.fade_in_card_slider)
        binding.tsMap.setOutAnimation(this, R.anim.fade_out_card_slider)
        binding.tsMap.setFactory(ImageViewFactory())
        binding.tsMap.setImageResource(viewModel.maps[0])

        mapLoadListener = object: DecodeBitmapTask.Listener{
            override fun onPostExecuted(bitmap: Bitmap?) {
                (binding.tsMap.nextView as ImageView).setImageBitmap(bitmap)
                binding.tsMap.showNext()
            }

        }
    }

    private fun initCountryText() {
        countryAnimDuration =
            resources.getInteger(R.integer.labels_animation_duration).toLong()
        countryOffset1 = resources.getDimensionPixelSize(R.dimen.left_offset)
        countryOffset2 = resources.getDimensionPixelSize(R.dimen.card_width)

        binding.tvCountry1.x = countryOffset1.toFloat()
        binding.tvCountry2.x = countryOffset2.toFloat()
        binding.tvCountry1.text = countries[0]
        binding.tvCountry2.alpha = 0f


        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", this)

        binding.tvCountry1.typeface = font
        binding.tvCountry2.typeface = font
    }

    private fun initGreenDot() {
        binding.tsMap.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.tsMap.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val viewLeft = binding.tsMap.left
                val viewTop = binding.tsMap.top + binding.tsMap.height / 3

                val border = 100
                val xRange = 1.coerceAtLeast(binding.tsMap.width - border * 2)
                val yRange = 1.coerceAtLeast((binding.tsMap.height / 3) * 2 - border * 2)

                val rnd = Random()
                //int i = 0, cnt = dotCoors.size; i < cnt; i++
                for (i in viewModel.dotCoors.indices) {
                    viewModel.dotCoors[i][0] = viewLeft + border + rnd.nextInt(xRange)
                    viewModel.dotCoors[i][1] = viewTop + border + rnd.nextInt(yRange)
                }

                binding.greenDot.x = viewModel.dotCoors[0][0].toFloat()
                binding.greenDot.y = viewModel.dotCoors[0][1].toFloat()
            }
        })
    }

    private fun setCountryText(text: String, left2right: Boolean) {
        val invisibleText: TextView
        val visibleText: TextView
        if (binding.tvCountry1.alpha > binding.tvCountry2.alpha) {
            visibleText = binding.tvCountry1
            invisibleText = binding.tvCountry2
        } else {
            visibleText = binding.tvCountry2
            invisibleText = binding.tvCountry1
        }

        val vOffset = if (left2right) {
            invisibleText.x = 0f
            countryOffset2
        } else {
            invisibleText.x = countryOffset2.toFloat()
            0
        }

        invisibleText.text = text

        val iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f)
        val vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f)
        val iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1.toFloat())
        val vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset.toFloat())

        val animSet = AnimatorSet()
        animSet.playTogether(iAlpha, vAlpha, iX, vX)
        animSet.duration = countryAnimDuration
        animSet.start()
    }

    private fun onActiveCardChange() {
        val pos = layoutManger!!.activeCardPosition
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return
        }

        onActiveCardChange(pos)
    }

    private fun onActiveCardChange(pos: Int) {
        val animH = intArrayOf(R.anim.slide_in_right_card_slider, R.anim.slide_out_left_card_slider)
        val animV = intArrayOf(R.anim.slide_in_top_card_slider, R.anim.slide_out_bottom_card_slider)

        val left2right = pos < currentPosition
        if (left2right) {
            animH[0] = R.anim.slide_in_left_card_slider
            animH[1] = R.anim.slide_out_right_card_slider

            animV[0] = R.anim.slide_in_bottom_card_slider
            animV[1] = R.anim.slide_out_top_card_slider
        }

        setCountryText(countries[pos % countries.size], left2right)

        binding.tsTemperature.setInAnimation(this, animH[0])
        binding.tsTemperature.setOutAnimation(this, animH[1])
        binding.tsTemperature.setText(viewModel.temperatures[pos % viewModel.temperatures.size])

        binding.tsPlace.setInAnimation(this, animV[0])
        binding.tsPlace.setOutAnimation(this, animV[1])
        binding.tsPlace.setText(places[pos % places.size])

        binding.tsClock.setInAnimation(this, animV[0])
        binding.tsClock.setOutAnimation(this, animV[1])
        binding.tsClock.setText(times[pos % times.size])

        binding.tsDescription.setText(getString(viewModel.descriptions[pos % viewModel.descriptions.size]))

        showMap(viewModel.maps[pos % viewModel.maps.size])

        ViewCompat.animate(binding.greenDot)
                .translationX(viewModel.dotCoors[pos % viewModel.dotCoors.size][0].toFloat())
                .translationY(viewModel.dotCoors[pos % viewModel.dotCoors.size][1].toFloat())
                .start()

        currentPosition = pos
    }

    private fun showMap(@DrawableRes resId: Int) {
        decodeMapBitmapTask?.cancel(true)

        val w = binding.tsMap.width
        val h = binding.tsMap.height

        decodeMapBitmapTask = DecodeBitmapTask(resources, resId, w, h, mapLoadListener!!)
        decodeMapBitmapTask!!.execute()
    }
    private inner class TextViewFactory internal constructor(
        @field:StyleRes @param:StyleRes val styleId: Int, val center: Boolean
    ) :
        ViewSwitcher.ViewFactory {
        override fun makeView(): View {
            val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileRegular.ttf", this@MissionListActivity)
            val textView = TextView(this@MissionListActivity)
            if (center) {
                textView.gravity = Gravity.CENTER
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                @Suppress("DEPRECATION")
                textView.setTextAppearance(this@MissionListActivity, styleId)
            } else {
                textView.setTextAppearance(styleId)
            }
            textView.typeface = font
            return textView
        }

    }
    private inner class ImageViewFactory : ViewSwitcher.ViewFactory {
        override fun makeView(): View {
            val imageView = ImageView(this@MissionListActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            val lp: ViewGroup.LayoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.layoutParams = lp
            return imageView
        }
    }

    private inner class OnCardClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val lm =
                binding.recyclerView.layoutManager as CardSliderLayoutManager
            if (lm.isSmoothScrolling) {
                return
            }
            val activeCardPosition = lm.activeCardPosition
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return
            }
            val clickedPosition: Int = binding.recyclerView.getChildAdapterPosition(view)
            if (clickedPosition == activeCardPosition) {
                //Todo: Add Opening of DetailActivity
                println("Should open next activity")
                //Open Detail Activity
//                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
//                intent.putExtra(
//                    DetailsActivity.BUNDLE_IMAGE_ID,
//                    pics.get(activeCardPosition % pics.size)
//                )
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(intent)
//                } else {
//                    val cardView = view as CardView
//                    val sharedView =
//                        cardView.getChildAt(cardView.childCount - 1)
//                    val options = ActivityOptions
//                        .makeSceneTransitionAnimation(this@MissionListActivity, sharedView, "shared")
//                    startActivity(intent, options.toBundle())
//                }
            } else if (clickedPosition > activeCardPosition) {
                binding.recyclerView.smoothScrollToPosition(clickedPosition)
                onActiveCardChange(clickedPosition)
            }
        }
    }
}