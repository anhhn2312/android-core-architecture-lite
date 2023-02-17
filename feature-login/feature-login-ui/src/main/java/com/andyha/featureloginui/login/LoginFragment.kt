package com.andyha.featureloginui.login

import android.animation.Animator
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andyha.coreextension.hideKeyboard
import com.andyha.coreui.base.fragment.BaseFragment
import com.andyha.coreui.base.fragment.ToolbarConfiguration
import com.andyha.coreui.base.theme.ThemeManager
import com.andyha.coreutils.setOnDebounceClick
import com.andyha.featureloginui.R
import com.andyha.featureloginui.databinding.FragmentLoginBinding
import com.jakewharton.rxbinding4.widget.editorActionEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.sqrt


class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>({ FragmentLoginBinding.inflate(it) }) {

    override fun getToolbarConfiguration() =
        ToolbarConfiguration.Builder()
            .setDefaultToolbarEnabled(false)
            .setHasBackButton(false)
            .setHasRefreshLayout(false)
            .build()

    private var changeThemeAnim: Animator? = null

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        bindAction()
    }

    @SuppressLint("CheckResult")
    private fun initUI() {
        viewBinding().apply {
            etPassword.editorActionEvents {
                if(it.actionId == EditorInfo.IME_ACTION_DONE){
                    login()
                    return@editorActionEvents true
                }
                return@editorActionEvents false
            }

            btnLogin.setOnDebounceClick {
                it.hideKeyboard()
                login()
            }

            ivLanguage.setOnDebounceClick {
                viewModel.toggleLanguage(requireActivity())
            }

            ivTheme.setOnDebounceClick {
                changeTheme()
            }
        }
    }

    private fun bindAction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserAuthorized.filter { it }.collectLatest {
                    viewBinding().btnLogin.stopAnimationToIdle { navigateToWeather()  }
                }
            }
        }
    }

    private fun login(){
        if (validateLogin()) {
            viewBinding().btnLogin.startAnimation()
            viewModel.login(viewBinding().etUsername.text.toString().trim())
        }
    }

    private fun validateLogin(): Boolean {
        if (viewBinding().etUsername.text?.isEmpty() == true) {
            viewBinding().etUsername.error = getString(R.string.message_username_empty)
            return false
        }
        if (viewBinding().etPassword.text?.isEmpty() == true) {
            viewBinding().etPassword.error = getString(R.string.message_password_empty)
            return false
        }
        return true
    }

    private fun navigateToWeather(){
        appNavigator.navigateToWeather()
        activity?.finish()
    }

    override fun onLocaleChanged() {
        super.onLocaleChanged()
        viewBinding().ivLanguage.setImageResource(R.drawable.ic_language_flag)
    }

    private fun changeTheme() {
        viewBinding().apply {
            val newTheme = viewModel.toggleTheme()

            if (behindFakeThemeImageView.visibility == View.VISIBLE || isRunningChangeThemeAnimation()) {
                return
            }

            // take screenshot
            val w = root.measuredWidth
            val h = root.measuredHeight
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            root.draw(canvas)

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
                    ThemeManager.postChangeTheme(requireActivity() as LoginActivity, newTheme)
                }

                override fun onAnimationCancel(animation: Animator) {
                    viewBinding().ivTheme.isEnabled = true
                    ThemeManager.postChangeTheme(requireActivity() as LoginActivity, newTheme)
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })

            changeThemeAnim?.start()

            // update theme
            ThemeManager.setTheme(requireActivity() as LoginActivity, newTheme)
        }
    }

    private fun isRunningChangeThemeAnimation(): Boolean {
        return changeThemeAnim?.isRunning == true
    }
}