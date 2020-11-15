package com.martinezdputra.wheel.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding

abstract class CoreActivity<VM: CoreViewModel>: AppCompatActivity() {
    private lateinit var mViewModel: VM

    abstract fun createViewModel(): VM
    abstract fun onInitView(viewModel: VM): ViewDataBinding
    abstract fun injectComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        mViewModel = createViewModel()
        mViewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                onViewModelChanged(sender, propertyId)
            }
        })
        val viewBinding = onInitView(mViewModel)
        viewBinding.lifecycleOwner = this
        setContentView(viewBinding.root)
    }

    val viewModel: VM
        get() = mViewModel

    open fun onViewModelChanged(sender: Observable?, id: Int) {

    }
}