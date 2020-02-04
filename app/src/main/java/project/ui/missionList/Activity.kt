package project.ui.missionList

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Typeface
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
    //TODO: Add data binding and move arrays to view model


    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: MissionListViewModel
//    private final String[] countries = {"PARIS", "SEOUL", "LONDON", "BEIJING", "THIRA"};
    private lateinit var countries: Array<String>
//        = arrayOf("پاریس", "سوول", "لندن", "بیژینگ", "ثیرا")
    //    private final String[] places = {"The Louvre", "Gwanghwamun", "Tower Bridge", "Temple of Heaven", "Aegeana Sea"};
    private lateinit var places: Array<String>
//            =        arrayOf("لوور", "گوانگوامون", "برج پل", "معبد بهشت", "دریای آجینا")
//    private val temperatures =
//        arrayOf("21°C", "19°C", "17°C", "23°C", "20°C")
    //private final String[] times = {"Aug 1 - Dec 15    7:00-18:00", "Sep 5 - Nov 10    8:00-16:00", "Mar 8 - May 21    7:00-18:00"};
    private lateinit var times: Array<String>
//        = arrayOf(
//        "۱ اردیبهشت - ۱۵ خرداد  ۷:۰۰-۱۸:۰۰",
//        "۵ تیر - ۱۰ مرداد  ۸:۰۰-۱۶:۰۰",
//        "۸بهمن - ۲۱ اسفند  ۷:۰۰-۱۸:۰۰"
//    )

    private val sliderAdapter: SliderAdapter = SliderAdapter(
        viewModel.pics,
        20,
        OnCardClickListener()
    )

    private var layoutManger: CardSliderLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var mapSwitcher: ImageSwitcher? = null
    private var temperatureSwitcher: TextSwitcher? = null
    private var placeSwitcher: TextSwitcher? = null
    private var clockSwitcher: TextSwitcher? = null
    private var descriptionsSwitcher: TextSwitcher? = null
    private var greenDot: View? = null

    private var country1TextView: TextView? = null
    private var country2TextView: TextView? = null
    private var countryOffset1 = 0
    private var countryOffset2 = 0
    private var countryAnimDuration: Long = 0
    private var currentPosition = 0

    private var decodeMapBitmapTask: DecodeBitmapTask? = null
    private var mapLoadListener: DecodeBitmapTask.Listener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

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
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.adapter = sliderAdapter
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange()
                }
            }
        })
        layoutManger = recyclerView!!.layoutManager as CardSliderLayoutManager?
        CardSnapHelper().attachToRecyclerView(recyclerView)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing && decodeMapBitmapTask != null) {
            decodeMapBitmapTask!!.cancel(true)
        }
    }

    private fun initSwitchers(){

        temperatureSwitcher = findViewById<View>(R.id.ts_temperature) as TextSwitcher
        temperatureSwitcher!!.setFactory(
            TextViewFactory(
                R.style.TemperatureTextView,
                true
            )
        )
        temperatureSwitcher!!.setCurrentText(viewModel.temperatures[0])


        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", this)

        placeSwitcher = findViewById<View>(R.id.ts_place) as TextSwitcher
        placeSwitcher!!.setFactory(
            TextViewFactory(
                R.style.PlaceTextView,
                false
            )
        )
        placeSwitcher!!.setCurrentText(places[0])

        clockSwitcher = findViewById<View>(R.id.ts_clock) as TextSwitcher
        clockSwitcher!!.setFactory(
            TextViewFactory(
                R.style.ClockTextView,
                false
            )
        )
        clockSwitcher!!.setCurrentText(times[0])

        descriptionsSwitcher = findViewById<View>(R.id.ts_description) as TextSwitcher
        descriptionsSwitcher!!.setInAnimation(this, android.R.anim.fade_in)
        descriptionsSwitcher!!.setOutAnimation(this, android.R.anim.fade_out)
        descriptionsSwitcher!!.setFactory(
            TextViewFactory(
                R.style.DescriptionTextView,
                false
            )
        )
        descriptionsSwitcher!!.setCurrentText(getString(viewModel.descriptions[0]))
        mapSwitcher = findViewById<View>(R.id.ts_map) as ImageSwitcher
        mapSwitcher!!.setInAnimation(this, R.anim.fade_in)
        mapSwitcher!!.setOutAnimation(this, R.anim.fade_out)
        mapSwitcher!!.setFactory(ImageViewFactory())
        mapSwitcher!!.setImageResource(viewModel.maps[0])

        mapLoadListener = object: DecodeBitmapTask.Listener{
            override fun onPostExecuted(bitmap: Bitmap?) {
                (mapSwitcher!!.nextView as ImageView).setImageBitmap(bitmap)
                mapSwitcher!!.showNext()
            }

        }
    }

    private fun initCountryText() {
        countryAnimDuration =
            resources.getInteger(R.integer.labels_animation_duration).toLong()
        countryOffset1 = resources.getDimensionPixelSize(R.dimen.left_offset)
        countryOffset2 = resources.getDimensionPixelSize(R.dimen.card_width)
        country1TextView = findViewById<View>(R.id.tv_country_1) as TextView
        country2TextView = findViewById<View>(R.id.tv_country_2) as TextView

        country1TextView!!.x = countryOffset1.toFloat()
        country2TextView!!.x = countryOffset2.toFloat()
        country1TextView!!.text = countries[0]
        country2TextView!!.alpha = 0f


        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", this)

        country1TextView!!.typeface = font
        country2TextView!!.typeface = font
    }

    private fun initGreenDot() {
        mapSwitcher!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mapSwitcher!!.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val viewLeft = mapSwitcher!!.left
                val viewTop = mapSwitcher!!.top + mapSwitcher!!.height / 3

                val border = 100
                val xRange = 1.coerceAtLeast(mapSwitcher!!.width - border * 2)
                val yRange = 1.coerceAtLeast((mapSwitcher!!.height / 3) * 2 - border * 2)

                val rnd = Random()
                //int i = 0, cnt = dotCoors.size; i < cnt; i++
                for (i in viewModel.dotCoors.indices) {
                    viewModel.dotCoors[i][0] = viewLeft + border + rnd.nextInt(xRange)
                    viewModel.dotCoors[i][1] = viewTop + border + rnd.nextInt(yRange)
                }

                greenDot = findViewById(R.id.green_dot)
                greenDot!!.x = viewModel.dotCoors[0][0].toFloat()
                greenDot!!.y = viewModel.dotCoors[0][1].toFloat()
            }
        })
    }

    private fun setCountryText(text: String, left2right: Boolean) {
        val invisibleText: TextView
        val visibleText: TextView
        if (country1TextView!!.alpha > country2TextView!!.alpha) {
            visibleText = country1TextView!!
            invisibleText = country2TextView!!
        } else {
            visibleText = country2TextView!!
            invisibleText = country1TextView!!
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

        temperatureSwitcher!!.setInAnimation(this, animH[0])
        temperatureSwitcher!!.setOutAnimation(this, animH[1])
        temperatureSwitcher!!.setText(viewModel.temperatures[pos % viewModel.temperatures.size])

        placeSwitcher!!.setInAnimation(this, animV[0])
        placeSwitcher!!.setOutAnimation(this, animV[1])
        placeSwitcher!!.setText(places[pos % places.size])

        clockSwitcher!!.setInAnimation(this, animV[0])
        clockSwitcher!!.setOutAnimation(this, animV[1])
        clockSwitcher!!.setText(times[pos % times.size])

        descriptionsSwitcher!!.setText(getString(viewModel.descriptions[pos % viewModel.descriptions.size]))

        showMap(viewModel.maps[pos % viewModel.maps.size])

        ViewCompat.animate(greenDot!!)
                .translationX(viewModel.dotCoors[pos % viewModel.dotCoors.size][0].toFloat())
                .translationY(viewModel.dotCoors[pos % viewModel.dotCoors.size][1].toFloat())
                .start()

        currentPosition = pos
    }

    private fun showMap(@DrawableRes resId: Int) {
        decodeMapBitmapTask?.cancel(true)

        val w = mapSwitcher!!.width
        val h = mapSwitcher!!.height

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
                recyclerView!!.layoutManager as CardSliderLayoutManager
            if (lm.isSmoothScrolling) {
                return
            }
            val activeCardPosition = lm.activeCardPosition
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return
            }
            val clickedPosition: Int = recyclerView!!.getChildAdapterPosition(view)
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
                recyclerView!!.smoothScrollToPosition(clickedPosition)
                onActiveCardChange(clickedPosition)
            }
        }
    }
}




