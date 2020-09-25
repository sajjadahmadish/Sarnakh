package project.ui.ar

import android.Manifest
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.easyar.Engine
import project.ui.base.BaseActivity
import javax.inject.Inject
import ir.sinapp.sarnakh.R
import ir.sinapp.sarnakh.databinding.ActivityArBinding
import project.utils.AppLogger
import project.utils.ar.GLView
import android.view.ViewGroup
import android.view.Window
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.jakewharton.rxbinding3.view.clicks
import ir.sinapp.sarnakh.databinding.ActivityLoginBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import project.ui.successDialog.SuccessDialogFragment
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import dagger.hilt.android.AndroidEntryPoint


@RuntimePermissions
@AndroidEntryPoint
class ARActivity : BaseActivity<ActivityArBinding, ARViewModel>(ActivityArBinding::class.java),
    ARNavigator {


    override val viewModel: ARViewModel by viewModels { this.defaultViewModelProviderFactory }

    lateinit var glView: GLView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(ActivityArBinding.inflate(layoutInflater))
        viewModel.navigator = this

        showCameraWithPermissionCheck()


        if (!Engine.initialize(this, getString(R.string.ar_api_key))) {
            AppLogger.e("HelloAR", "Initialization Failed.");
            Toast.makeText(this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }


        viewModel += binding.btnAnswer.clicks().subscribe {
            openAnswerActivity()
        }

    }


    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCamera() {

        glView = GLView(this)

        binding.preview.addView(
            glView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }


    override fun openAnswerActivity() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_question)
        val editText = dialog.findViewById<ExtendedEditText>(R.id.answer)
        val btn = dialog.findViewById<View>(R.id.bt_try)
        viewModel += btn.clicks().subscribe {
            dialog.dismiss()
            if (editText.text.toString() == "علی بابا") {
                val fragmentManager = supportFragmentManager
                val newFragment = SuccessDialogFragment()
                val transaction =
                    fragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()
            } else {
                Toast.makeText(this, "wrong", Toast.LENGTH_LONG).show()
            }
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }

}




