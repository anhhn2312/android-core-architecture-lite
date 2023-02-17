package com.andyha.featureweatherui.ui.activity

import android.animation.Animator
import android.content.Intent
import android.content.IntentSender
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andyha.coreui.base.activity.BaseActivity
import com.andyha.coreui.base.theme.ThemeManager
import com.andyha.coreui.base.theme.toggleTheme
import com.andyha.coreutils.setOnDebounceClick
import com.andyha.featureweatherkit.data.model.LocationState
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.databinding.ActivityWeatherBinding
import com.andyha.featureweatherui.ui.weather.locationHistory.LocationHistoryBottomSheetFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.sqrt


class WeatherActivity :
    BaseActivity<ActivityWeatherBinding>({ ActivityWeatherBinding.inflate(it) }) {

    private val viewModel: WeatherViewModel by viewModels()

    private var changeThemeAnim: Animator? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initUI()
        bindData()
    }

    private fun initUI() {
        viewBinding().apply {
            rlLocation.setOnDebounceClick {
                LocationHistoryBottomSheetFragment.show(supportFragmentManager) {
                    viewModel.selectLocation(it)
                }
            }

            ivTheme.setOnDebounceClick {
                changeTheme()
            }

            ivLanguage.setOnDebounceClick {
                viewModel.toggleLanguage(this@WeatherActivity)
            }

            ivLogout.setOnDebounceClick {
                viewModel.logout()
            }
        }
        setupBottomNavigationView()
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentLocationState.collect {
                    if (it is LocationState.LocationSettingOff) {
                        try {
                            it.exception.startResolutionForResult(
                                this@WeatherActivity,
                                REQUEST_CHANGE_LOCATION_SETTINGS
                            )
                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Ignore the error.
                        }
                    }

                    viewBinding().apply {
                        if (it is LocationState.LocationDetected) {
                            tvLocation.text = it.address
                            tvCountry.isVisible = true
                            tvCountry.text = it.region.ifEmpty { it.country }
                        } else {
                            tvLocation.setCustomText(R.string.detecting_location)
                            tvCountry.isVisible = false
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logoutSucessful.filter { it }.collect {
                    appNavigator.navigateToLogin()
                    finish()
                }
            }
        }
    }

    private fun setupBottomNavigationView() {
        viewBinding().navView.setupWithActivity(this)
    }

    private fun changeTheme() {
        viewBinding().apply {
            val newTheme = preference.toggleTheme()

            if (behindFakeThemeImageView.visibility == View.VISIBLE || isRunningChangeThemeAnimation()) {
                return
            }

            // take screenshot
            val w = content.measuredWidth
            val h = content.measuredHeight
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            content.draw(canvas)

            //create anim
            val finalRadius = sqrt((w * w + h * h).toDouble()).toFloat()
            behindFakeThemeImageView.bitmap = bitmap
            behindFakeThemeImageView.visibility = View.VISIBLE
            changeThemeAnim = ViewAnimationUtils.createCircularReveal(
                content,
                ivTheme.x.toInt(),
                ivTheme.y.toInt(),
                0f,
                finalRadius
            )

            // set duration
            changeThemeAnim?.duration = 600

            // set listener
            changeThemeAnim?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    viewBinding().ivTheme.isEnabled = false
                }

                override fun onAnimationEnd(animation: Animator) {
                    behindFakeThemeImageView.bitmap = null
                    behindFakeThemeImageView.visibility = View.GONE
                    viewBinding().ivTheme.isEnabled = true
                    ThemeManager.postChangeTheme(this@WeatherActivity, newTheme)
                }

                override fun onAnimationCancel(animation: Animator) {
                    viewBinding().ivTheme.isEnabled = true
                    ThemeManager.postChangeTheme(this@WeatherActivity, newTheme)
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })

            changeThemeAnim?.start()

            // update theme
            ThemeManager.setTheme(this@WeatherActivity, newTheme)
        }
    }

    private fun isRunningChangeThemeAnimation(): Boolean {
        return changeThemeAnim?.isRunning == true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHANGE_LOCATION_SETTINGS && resultCode == RESULT_OK) {
            viewModel.requestLocationUpdate()
        }
    }

    override fun onLocaleChanged() {
        super.onLocaleChanged()
        viewBinding().navView.updateMenu()
        viewBinding().ivLanguage.setImageResource(R.drawable.ic_language_flag)
    }

    companion object {
        const val REQUEST_CHANGE_LOCATION_SETTINGS = 100
    }
}