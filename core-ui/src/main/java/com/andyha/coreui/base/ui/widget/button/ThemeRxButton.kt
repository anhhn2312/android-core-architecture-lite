package com.andyha.coreui.base.ui.widget.button

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.andyha.coreui.R
import com.andyha.coreui.base.theme.TextColorAttr
import com.andyha.coreui.base.theme.ThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


open class ThemeRxButton: BaseButton {
    private var coroutineScope: CoroutineScope? = null

    private var textColorAttr: Int? = null
        set(value) {
            field = if (value == 0) null
            else value
        }

    private var backgroundDrawableAttr: Int? = null
        set(value) {
            field = if (value == 0) null
            else value
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val styleArray = context.obtainStyledAttributes(attrs, R.styleable.ThemeRx)
        textColorAttr =
            styleArray.getInt(R.styleable.ThemeRx_TRX_textColor, TextColorAttr.Primary.value)
        backgroundDrawableAttr = styleArray.getInt(R.styleable.ThemeRx_TRX_backgroundDrawable, 0)

        styleArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        onThemeChanged()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            ThemeManager.onThemeChanged.collectLatest { onThemeChanged() }
        } ?: kotlin.run {
            coroutineScope = CoroutineScope(Dispatchers.Main + Job()).apply {
                launch {
                    ThemeManager.onThemeChanged.collectLatest { onThemeChanged() }
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        coroutineScope?.cancel()
    }

    private fun onThemeChanged() {
        textColorAttr?.let {
            ThemeManager.getTextColor(context, it)?.let { textColor ->
                setTextColor(textColor)
                compoundDrawables.map {
                    it?.mutate()?.apply { setTint(textColor) }
                }.let { newCompoundDrawables ->
                    setCompoundDrawables(
                        newCompoundDrawables[0],
                        newCompoundDrawables[1],
                        newCompoundDrawables[2],
                        newCompoundDrawables[3]
                    )
                }
            }
        }
        backgroundDrawableAttr?.let {
            ThemeManager.getBackgroundDrawawble(context, it)?.let {
                background = it
            }
        }
    }

}