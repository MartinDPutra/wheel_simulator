package com.martinezdputra.wheel.screen

import android.content.Context
import androidx.databinding.Bindable
import com.martinezdputra.wheel.BR
import com.martinezdputra.wheel.R
import com.martinezdputra.wheel.core.CoreViewModel
import com.martinezdputra.wheel.repository.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomepageViewModel @Inject constructor(private val apiRepository: ApiRepository, private val context: Context): CoreViewModel() {
    var rpm = 0
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.rpm)
        }

    var loading = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    var errorMessage: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

    fun fetchApi() {
        loading = true
        compositeDisposable.add(
            apiRepository.getRandomNumber()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { loading = false }
            .subscribe({
                rpm = it
            }) {
                it.printStackTrace()
                errorMessage = context.getString(R.string.text_unable_to_update_speed)
            }
        )
    }

    fun getRotationDuration(): Long {
        return (60F / rpm.toFloat() * 1000F).toLong()
    }
}