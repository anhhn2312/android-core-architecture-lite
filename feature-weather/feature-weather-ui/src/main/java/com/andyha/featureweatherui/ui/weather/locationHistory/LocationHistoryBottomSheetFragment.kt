package com.andyha.featureweatherui.ui.weather.locationHistory

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andyha.coreextension.TAG
import com.andyha.coreextension.utils.DimensionUtils
import com.andyha.coreui.base.ui.widget.bottomsheet.BaseBottomSheetDialogFragment
import com.andyha.coreui.base.ui.widget.recyclerview.itemdecoration.RecyclerViewVerticalSpacing
import com.andyha.featureweatherkit.data.model.LocationState.LocationDetected
import com.andyha.featureweatherui.R
import com.andyha.featureweatherui.databinding.BottomSheetLocationHistoryBinding
import com.andyha.featureweatherui.ui.weather.locationHistory.adapter.LocationHistoryAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationHistoryBottomSheetFragment :
    BaseBottomSheetDialogFragment<BottomSheetLocationHistoryBinding, LocationHistoryViewModel>({
        BottomSheetLocationHistoryBinding.inflate(it)
    }) {

    private lateinit var onLocationClicked: (LocationDetected) -> Unit

    private val adapter by lazy {
        LocationHistoryAdapter {
            onLocationClicked.invoke(it)
            dismiss()
        }
    }

    override fun onFragmentCreated() {
        viewBinding.apply {
            initUI()
            bindData()
        }
    }

    private fun initUI() {
        viewBinding.apply {
            rvLocations.adapter = adapter
            rvLocations.addItemDecoration(
                RecyclerViewVerticalSpacing(
                    requireContext(),
                    spacingDimen = R.dimen.dimen0dp,
                    bottomOffset = R.dimen.dimen24dp
                )
            )
        }

        dialog?.setOnShowListener {
            initLayoutHeight()
        }

        setUpMaxHeightForPopup()
    }

    private fun bindData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locations.collectLatest {
                    adapter.submit(it)
                }
            }
        }
    }

    private fun initLayoutHeight() {
        val d = dialog as BottomSheetDialog
        val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val params: CoordinatorLayout.LayoutParams =
            bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
        val maxHeight = getPopupHeight(MAX_HEIGHT_PERCENTAGE)
        if (maxHeight < bottomSheet.height) {
            params.height = maxHeight
            bottomSheet.layoutParams = params
            behavior.peekHeight = maxHeight
        }
    }

    private fun setUpMaxHeightForPopup() {
        val maxHeight = getPopupHeight(MAX_HEIGHT_PERCENTAGE)
        viewBinding.root.maxHeight = maxHeight + 1
    }

    private fun getPopupHeight(percent: Float): Int {
        return (DimensionUtils.getScreenHeight() * percent).toInt()
    }

    companion object {
        private const val MAX_HEIGHT_PERCENTAGE = 0.5f

        fun show(
            fm: FragmentManager,
            onLocationClicked: (LocationDetected) -> Unit
        ) {
            val dialog = LocationHistoryBottomSheetFragment()
            dialog.titleRes = R.string.locations
            dialog.onLocationClicked = onLocationClicked
            dialog.show(fm, TAG)
        }
    }
}