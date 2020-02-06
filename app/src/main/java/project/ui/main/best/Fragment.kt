package project.ui.main.best


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import cn.nekocode.badge.BadgeDrawable
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentBestBinding
import project.ui.base.BaseFragment
import project.utils.CommonUtils
import javax.inject.Inject



class BestFragment : BaseFragment<FragmentBestBinding, BestViewModel>(FragmentBestBinding::class.java) {

    override val bindingVariable: Int get() = BR.viewModel

    @Inject
    override lateinit var viewModel: BestViewModel


    companion object {


        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", context)

        val yourScoreDrawable = BadgeDrawable.Builder()
            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
            .badgeColor(Color.parseColor("#F0C419"))
            .text1(" رتبه شما ").typeFace(font).build()

        binding.yourRateBadge.setImageDrawable(yourScoreDrawable)

        fun newInstance(): BestFragment {
            val args = Bundle()
            val fragment = BestFragment()
            fragment.arguments = args
            return fragment
        }

    }


}






