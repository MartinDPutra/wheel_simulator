package com.martinezdputra.wheel.screen

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.martinezdputra.wheel.BR
import com.martinezdputra.wheel.R
import com.martinezdputra.wheel.core.CoreActivity
import com.martinezdputra.wheel.databinding.HomepageActivityBinding
import com.martinezdputra.wheel.di.component.DaggerAppComponent
import javax.inject.Inject


class HomepageActivity: CoreActivity<HomepageViewModel>() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var mBinding: HomepageActivityBinding

    override fun createViewModel(): HomepageViewModel {
        return factory.create(HomepageViewModel::class.java)
    }

    override fun onInitView(viewModel: HomepageViewModel): ViewDataBinding {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.homepage_activity, null, false)
        mBinding.viewModel = viewModel

        initViewComponents()

        return mBinding
    }

    override fun injectComponent() {
        DaggerAppComponent
            .builder()
            .application(application)
            .build()
            .inject(this)
    }

    override fun onViewModelChanged(sender: Observable?, id: Int) {
        super.onViewModelChanged(sender, id)
        when (id) {
            BR.rpm -> updateRpm()
            BR.errorMessage -> showSnackbar(viewModel.errorMessage)
        }
    }

    private fun initViewComponents() {
        mBinding.button.setOnClickListener {
            showSnackbar(applicationContext.getString(R.string.text_fetching_rpm))
            viewModel.fetchApi()
        }
    }

    private fun showSnackbar(message: String?) {
        if(!message.isNullOrEmpty()) {
            val snackbar = Snackbar.make(mBinding.layoutContainer, message, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    private fun updateRpm() {
        if(viewModel.rpm != 0) {
            showSnackbar(applicationContext.getString(R.string.text_speed_updated))
            val animation = RotateAnimation(
                0F, 360F,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            animation.duration = viewModel.getRotationDuration()
            animation.repeatCount = Animation.INFINITE
            animation.interpolator = LinearInterpolator()
            mBinding.imageView.startAnimation(animation)
        }
    }
}