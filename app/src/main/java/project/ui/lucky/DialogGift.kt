package project.ui.lucky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ir.sinapp.sarnakh.databinding.DialogGiftBinding


class DialogGift : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = DialogGiftBinding.inflate(layoutInflater, container, false)
        return inflate.root
    }

}