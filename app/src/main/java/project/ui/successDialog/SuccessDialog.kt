package project.ui.successDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.hsalf.smilerating.BaseRating
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.DialogSuccessBinding

class SuccessDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogSuccessBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_success, null, false)

        binding.smileRating.apply {
            setNameForSmile(BaseRating.TERRIBLE, context!!.resources.getString(R.string.terrible))
            setNameForSmile(BaseRating.BAD, context!!.resources.getString(R.string.bad))
            setNameForSmile(BaseRating.OKAY, context!!.resources.getString(R.string.okay))
            setNameForSmile(BaseRating.GOOD, context!!.resources.getString(R.string.good))
            setNameForSmile(BaseRating.GREAT, context!!.resources.getString(R.string.great))
        }
        binding.fab.setOnClickListener {
            dismiss()
            activity?.finish()
        }
        return binding.root
    }
}