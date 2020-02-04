package project.ui.intro

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ArrayRes
import androidx.viewpager.widget.PagerAdapter
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ItemStepperWizardColorBinding
import java.util.*
import javax.inject.Inject

/**
 * View pager adapter
 */

class MyViewPagerAdapter @Inject constructor(val context: Context) : PagerAdapter() {

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val titleArray: List<String>
    private val descriptionArray: List<String>
    private val colorArray: List<Int>
    private val imageArray: List<Int>

    init {
        titleArray = getStrings(R.array.title_intro)
        descriptionArray = getStrings(R.array.description_intro)
        imageArray = listOf(R.drawable.img_wizard_1, R.drawable.img_wizard_2, R.drawable.img_wizard_3, R.drawable.img_wizard_4)
        colorArray =
            listOf(R.color.amber_500, R.color.cyan_500, R.color.purple_500, R.color.red_500)
    }


    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val binding = ItemStepperWizardColorBinding.inflate(layoutInflater, container, false)

        binding.title.text = titleArray[position]
        binding.description.text = descriptionArray[position]
        binding.image.setActualImageResource(imageArray[position])
        binding.lyt.setBackgroundColor(context.resources.getColor(colorArray[position]))

//        (view.findViewById(R.id.title)).text = title_array[position]
//        (view.findViewById(R.id.description)).text = description_array[position]
//        (view.findViewById(R.id.image)).setImageResource(about_images_array[position])
//        view.findViewById(R.id.lyt_parent)
//            .setBackgroundColor(resources.getColor(color_array[position]))
//        container.addView(view)
//
//        return view
        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int {
        return titleArray.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }


    fun getStrings(@ArrayRes r: Int): List<String> {
        val items = ArrayList<String>()
        val arr = context.resources.getStringArray(r)
        Collections.addAll(items, *arr)
        items.shuffle()
        return items
    }


}
