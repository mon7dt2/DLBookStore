package com.base.mvvmbasekotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.base.mvvmbasekotlin.R
import com.base.mvvmbasekotlin.custom.LoadingDialog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

abstract class BaseFragment(@ApplicationContext val contextFragment: Context) : Fragment() {
    private val TAG = this::class.java.name
    private var viewController : ViewController? = null

    protected val loadingDialog : LoadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    abstract fun backFromAddFragment()

    @get: LayoutRes
    protected abstract val layoutId :  Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    abstract fun backPressed() : Boolean

    fun setVC(viewController: ViewController) {
        this.viewController = viewController
    }

    fun getVC() : ViewController {
        if(viewController == null){
            viewController =  (activity as BaseActivity).getViewController()
        }

        return viewController!!
    }

    protected fun transitFragment(
        fragment: BaseFragment,
        @IdRes id: Int,
        args: Bundle? = null,
        @AnimRes enterAnim: Int = R.anim.trans_left_in,
        @AnimRes exitAnim: Int = R.anim.trans_left_out,
        @AnimRes popEnter: Int = R.anim.trans_right_in,
        @AnimRes popExit: Int = R.anim.trans_right_out,
    ) {
        val fragmentManager = activity?.supportFragmentManager
        args?.let {
            fragment.arguments = args
        }
        fragmentManager?.beginTransaction()?.setCustomAnimations(
            enterAnim,  // enter
            exitAnim,  // exit
            popEnter,   // popEnter
            popExit  // popExit
        )?.add(id, fragment, fragment.javaClass.name)?.addToBackStack(fragment.TAG)?.commit()
    }

    protected fun replaceFragment(
        fragment: BaseFragment,
        @IdRes id: Int,
        args: Bundle? = null,
        animation: Boolean = true
    ) {
        val fragmentManager = activity?.supportFragmentManager
        args?.let {
            fragment.arguments = args
        }
        if (animation) {
            fragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.trans_left_in,  // enter
                R.anim.trans_left_out,  // exit
                R.anim.trans_right_in,   // popEnter
                R.anim.trans_right_out  // popExit
            )?.replace(id, fragment, fragment.javaClass.name)?.commit()
        } else {
            fragmentManager?.beginTransaction()?.replace(id, fragment, fragment.javaClass.name)
                ?.commit()
        }
    }

    protected fun transitChildFragment(
        parentFragment: Fragment,
        fragment: BaseFragment,
        @IdRes id: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = parentFragment.childFragmentManager
        fragment.arguments = args

        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.trans_left_in,  // enter
            R.anim.trans_left_out,  // exit
            R.anim.trans_right_in,   // popEnter
            R.anim.trans_right_out  // popExit
        ).add(id, fragment, fragment.javaClass.name).addToBackStack(fragment.TAG).commit()
    }

    fun transitFragment(
        fragment: BaseFragment,
        @IdRes id: Int,
        isAddToBackStack: Boolean = true,
        args: Bundle? = null
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()?.setCustomAnimations(
            R.anim.trans_left_in,  // enter
            R.anim.trans_left_out,  // exit
            R.anim.trans_right_in,   // popEnter
            R.anim.trans_right_out  // popExit
        )?.add(id, fragment, fragment.javaClass.name)?.addToBackStack(fragment.TAG)?.commit()
    }


    open fun <U> getObjResponse(data : U){

    }

    open fun <U> getListResponse(data: List<U>?){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog.dismiss()
    }
}