package com.andyha.featureweatherui.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.andyha.coreextension.TAG
import com.andyha.coreextension.getColorAttr
import com.andyha.coreextension.getSimpleName
import com.andyha.coreui.base.activity.BaseActivity
import com.andyha.coreui.base.theme.ThemeManager
import com.andyha.coreui.manager.MotionManager
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.ui.weather.daily.DailyFragment
import com.andyha.featureweatherui.ui.weather.hourly.HourlyFragment
import com.andyha.featureweatherui.ui.weather.now.NowFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WeatherBottomNavView : BottomNavigationView {

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    private var currentTag = NowFragment.TAG

    private lateinit var activity: AppCompatActivity

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        disableItemLongPress()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope.launch {
            ThemeManager.onThemeChanged.collectLatest { onThemeChanged() }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }

    private fun showFragments(fragmentTag: String) {
        currentTag = if (currentTag.isEmpty()) {
            NowFragment.TAG
        } else {
            fragmentTag
        }

        hide(currentTag)

        activity.supportFragmentManager.findFragmentByTag(currentTag)
            ?.let { show(activity.supportFragmentManager, it) }
            ?: add(
                activity.supportFragmentManager,
                containerId = R.id.frameContainer,
                fragment = when (currentTag) {
                    NowFragment.TAG -> NowFragment.newInstance()
                    HourlyFragment.TAG -> HourlyFragment.newInstance()
                    else -> DailyFragment.newInstance()
                }, tag = currentTag
            )
    }

    private fun show(fragmentManager: FragmentManager?, fragment: Fragment) {
        fragment.enterTransition =
            MotionManager.buildSharedAxisTransition(MaterialSharedAxis.X, true)

        fragmentManager
            ?.beginTransaction()
            ?.show(fragment)
            ?.commitAllowingStateLoss()
    }

    private fun hide(excludeFragmentTag: String) {
        for (tag in FRAGMENT_TAG) {
            if (tag != excludeFragmentTag) {
                activity.supportFragmentManager.findFragmentByTag(tag)
                    ?.let { hide(activity.supportFragmentManager, it) }
            }
        }
    }

    private fun hide(fragmentManager: FragmentManager?, fragment: Fragment) {
        fragment.enterTransition =
            MotionManager.buildSharedAxisTransition(MaterialSharedAxis.X, true)

        fragmentManager
            ?.beginTransaction()
            ?.hide(fragment)
            ?.commit()
    }

    private fun add(
        fragmentManager: FragmentManager?,
        containerId: Int,
        fragment: Fragment,
        tag: String? = null
    ) {
        fragment.enterTransition =
            MotionManager.buildSharedAxisTransition(MaterialSharedAxis.X, true)

        fragmentManager
            ?.beginTransaction()
            ?.add(containerId, fragment, tag ?: fragment.getSimpleName())
            ?.commit()
    }

    private fun disableItemLongPress() {
        menu.forEach {
            findViewById<View>(it.itemId).setOnLongClickListener { true }
        }
    }

    fun updateMenu() {
        menu.forEach {
            when (it.itemId) {
                R.id.today_navigation -> it.setTitle(R.string.now)
                R.id.hourly_navigation -> it.setTitle(R.string.hourly)
                else -> it.setTitle(R.string.daily)
            }
        }
    }

    fun setupWithActivity(activity: BaseActivity<*>, defaultTag: String = NowFragment.TAG) {
        this.activity = activity

        setOnNavigationItemSelectedListener {

            if (activity.supportFragmentManager.backStackEntryCount > 0) {
                //if there are some fragments added on the top, remove them
                activity.supportFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }

            showFragments(
                when (it.itemId) {
                    R.id.today_navigation -> NowFragment.TAG
                    R.id.hourly_navigation -> HourlyFragment.TAG
                    else -> DailyFragment.TAG
                }
            )

            return@setOnNavigationItemSelectedListener true
        }

        showFragments(defaultTag)
    }

    private fun onThemeChanged() {
        setBackgroundColor(context.getColorAttr(R.attr.windowBackgroundVariant))
        itemIconTintList = context.getColorStateList(R.color.color_bottom_nav_text)
        itemTextColor = context.getColorStateList(R.color.color_bottom_nav_text)
    }

    companion object {
        private val FRAGMENT_TAG = arrayOf(
            NowFragment.TAG,
            HourlyFragment.TAG,
            DailyFragment.TAG,
        )
    }
}