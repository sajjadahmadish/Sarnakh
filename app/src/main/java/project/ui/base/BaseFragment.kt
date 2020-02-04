package project.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import project.App
import project.Session

/**
 * Created by amitshekhar on 09/07/17.
 */

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>>(private val bindingClass: Class<T>) :
    DaggerFragment() {

    var baseActivity: BaseActivity<*, *>? = null
        private set

    private var mRootView: View? = null


    private var viewDataBinding: T? = null

    lateinit var binding: T


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

//    /**
//     * @return layout resource id
//     */
//    @get:LayoutRes
//    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    protected val session: Session
        get() = App.get(baseActivity!!).session!!



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            val m = bindingClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            viewDataBinding = m.invoke(viewDataBinding, layoutInflater) as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding = viewDataBinding!!
        binding.lifecycleOwner = this


//        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = binding.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(bindingVariable, viewModel)
        binding.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }


    @Subscribe
    fun event(i: Int) {
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
