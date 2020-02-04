package project.ui.lucky

import android.os.Bundle
import android.widget.Toast
import com.jakewharton.rxbinding3.view.clicks
import project.ui.base.BaseActivity
import javax.inject.Inject

import ir.sinapp.sarnakh.BR
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityLuckyBinding
import ir.sinapp.sarnakh.databinding.DialogGiftBinding
import kotlinx.android.synthetic.main.activity_lucky.*
import project.utils.AppLogger
import project.utils.lucky.model.LuckyItem
import java.util.*
import android.graphics.drawable.ColorDrawable
import android.app.Dialog
import android.view.Window


class LuckyActivity : BaseActivity<ActivityLuckyBinding, LuckyViewModel>(ActivityLuckyBinding::class.java), LuckyNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel

    @Inject
    override lateinit var viewModel: LuckyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this

        val data = mutableListOf<LuckyItem>()

        val luckyItem1 = LuckyItem()
        luckyItem1.topText = "100"
        luckyItem1.icon = R.drawable.test1
        luckyItem1.color = -0xc20
        data.add(luckyItem1)

        val luckyItem2 = LuckyItem()
        luckyItem2.topText = "200"
        luckyItem2.icon = R.drawable.test2
        luckyItem2.color = -0x1f4e
        data.add(luckyItem2)

        val luckyItem3 = LuckyItem()
        luckyItem3.topText = "300"
        luckyItem3.icon = R.drawable.test3
        luckyItem3.color = -0x3380
        data.add(luckyItem3)

        //////////////////
        val luckyItem4 = LuckyItem()
        luckyItem4.topText = "400"
        luckyItem4.icon = R.drawable.test4
        luckyItem4.color = -0xc20
        data.add(luckyItem4)

        val luckyItem5 = LuckyItem()
        luckyItem5.topText = "500"
        luckyItem5.icon = R.drawable.test5
        luckyItem5.color = -0x1f4e
        data.add(luckyItem5)

        val luckyItem6 = LuckyItem()
        luckyItem6.topText = "600"
        luckyItem6.icon = R.drawable.test6
        luckyItem6.color = -0x3380
        data.add(luckyItem6)
        //////////////////

        //////////////////////
        val luckyItem7 = LuckyItem()
        luckyItem7.topText = "700"
        luckyItem7.icon = R.drawable.test7
        luckyItem7.color = -0xc20
        data.add(luckyItem7)

        val luckyItem8 = LuckyItem()
        luckyItem8.topText = "800"
        luckyItem8.icon = R.drawable.test8
        luckyItem8.color = -0x1f4e
        data.add(luckyItem8)


        val luckyItem9 = LuckyItem()
        luckyItem9.topText = "900"
        luckyItem9.icon = R.drawable.test9
        luckyItem9.color = -0x3380
        data.add(luckyItem9)
        ////////////////////////

        val luckyItem10 = LuckyItem()
        luckyItem10.topText = "1000"
        luckyItem10.icon = R.drawable.test10
        luckyItem10.color = -0x1f4e
        data.add(luckyItem10)

        val luckyItem11 = LuckyItem()
        luckyItem11.topText = "2000"
        luckyItem11.icon = R.drawable.test10
        luckyItem11.color = -0x1f4e
        data.add(luckyItem11)

        val luckyItem12 = LuckyItem()
        luckyItem12.topText = "3000"
        luckyItem12.icon = R.drawable.test10
        luckyItem12.color = -0x1f4e
        data.add(luckyItem12)

        /////////////////////

        lucky_wheel.setData(data)
        lucky_wheel.setRound(5)

        viewModel += binding.play.clicks().subscribe {
            val index = getRandomIndex(data)
            lucky_wheel.startLuckyWheelWithTargetIndex(index)
        }

        lucky_wheel.isTouchEnabled = false
        lucky_wheel.setLuckyRoundItemSelectedListener {
            showDialogProductBlue()
        }

    }

    private fun showDialogProductBlue() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_gift)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun getRandomIndex(data: List<LuckyItem>): Int {
        val rand = Random()
        return rand.nextInt(data.size - 1) + 0
    }

}


