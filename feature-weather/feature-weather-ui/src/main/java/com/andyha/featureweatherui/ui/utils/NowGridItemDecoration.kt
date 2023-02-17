package com.andyha.featureweatherui.ui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andyha.coreextension.dimen
import com.andyha.coreui.R

class NowGridItemDecoration(
    context: Context,
    private val spanCount: Int,
    private val orientation: Int = GridLayoutManager.VERTICAL,
    @DimenRes private val horizontalSpacingDimen: Int = R.dimen.dimen16dp,
    @DimenRes private val verticalSpacingDimen: Int = R.dimen.dimen16dp,
) : RecyclerView.ItemDecoration() {

    private val horizontalSpacing = context.dimen(horizontalSpacingDimen)
    private val verticalSpacing = context.dimen(verticalSpacingDimen)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = verticalSpacing

        val position = parent.getChildAdapterPosition(view)
        if (position == 0 || position == parent.adapter?.itemCount?.minus(1)) {
            outRect.left = horizontalSpacing
            outRect.right = horizontalSpacing
            return
        }

        val column = (position - 1) % spanCount

        if (column == 0) {
            outRect.left = horizontalSpacing
        } else {
            outRect.left = horizontalSpacing / 2
        }

        if (column == spanCount - 1) {
            outRect.right = horizontalSpacing
        } else {
            outRect.right = horizontalSpacing / 2
        }
    }
}