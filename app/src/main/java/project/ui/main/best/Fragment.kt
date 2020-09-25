package project.ui.main.best


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import cn.nekocode.badge.BadgeDrawable
import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.databinding.FragmentBestBinding
import kotlinx.android.synthetic.main.activity_main.*
import project.ui.base.BaseFragment
import project.utils.CommonUtils
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BestFragment: BaseFragment<FragmentBestBinding, BestViewModel>(FragmentBestBinding::class.java) {


    override val viewModel: BestViewModel by viewModels { this.defaultViewModelProviderFactory }


    companion object {


        fun newInstance(): BestFragment {
            val args = Bundle()
            val fragment = BestFragment()
            fragment.arguments = args
            return fragment
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val font = CommonUtils.typefaceFromAsset("fonts/IRANYekanMobileBold.ttf", context!!)

        val yourScoreDrawable = BadgeDrawable.Builder()
            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
            .badgeColor(Color.parseColor("#F0C419"))
            .text1(" رتبه شما ").typeFace(font).build()

        binding.yourRateBadge.setImageDrawable(yourScoreDrawable)

    }

}






