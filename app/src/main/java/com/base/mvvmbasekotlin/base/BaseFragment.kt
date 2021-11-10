package com.base.mvvmbasekotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var viewController : ViewController? = null

//    private val loadingDialog : LoadingDialog by lazy { LoadingDialog(requireContext()) }

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

    open fun <U> getObjResponse(data : U){

    }

    open fun <U> getListResponse(data: List<U>?){

    }

//    protected fun showLoadingDialog(){
//        loadingDialog.show()
//    }
//
//    protected fun hideLoadingDilog(){
//        loadingDialog.hide()
//    }

}