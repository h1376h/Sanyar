package com.sanyar.ui.qrreader

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.sanyar.model.Device
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.ParamsProvider
import com.sanyar.provider.SizeProvider
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRReaderView(context: Context, val devices: ArrayList<Device>?, val presenter: Presenter) : FrameLayout(context) {


    interface Presenter{
        fun onClick(device: Device)
    }

    lateinit var containerLinearLayout: LinearLayout
    lateinit var zXingScannerView: ZXingScannerView
    lateinit var linearLayout: LinearLayout
    lateinit var scrollView: NestedScrollView

    init {
        initView()
    }

    private fun initView() {
        containerLinearLayout = LinearLayout(context)
        containerLinearLayout.orientation = LinearLayout.VERTICAL
        addView(
            containerLinearLayout,
            ParamsProvider.Frame.getFullSizeParams()
        )
        if (!devices.isNullOrEmpty()){
            if (devices.size > 3){
                scrollView = NestedScrollView(context)
                containerLinearLayout.addView(
                    scrollView,
                    ParamsProvider.Linear.getParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        SizeProvider.getGEneralPadding() * 12
                    )
                )
                linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL
                scrollView.addView(
                    linearLayout,
                    ParamsProvider.Frame.getFullSizeParams()
                )
                devices.forEach {device ->
                    val deviceListItemView = DeviceListItemView(context)
                    deviceListItemView.setup(device)
                    linearLayout.addView(
                        deviceListItemView,
                        ParamsProvider.Linear.defaultParams
                    )
                    deviceListItemView.setOnClickListener {
                        presenter.onClick(device)
                    }
                }
            }else{
                linearLayout = LinearLayout(context)
                linearLayout.orientation = LinearLayout.VERTICAL
                containerLinearLayout.addView(
                    linearLayout,
                    ParamsProvider.Linear.defaultParams
                )
                devices.forEach {device ->
                    val deviceListItemView = DeviceListItemView(context)
                    deviceListItemView.setup(device)
                    linearLayout.addView(
                        deviceListItemView,
                        ParamsProvider.Linear.defaultParams
                    )
                    deviceListItemView.setOnClickListener {
                        presenter.onClick(device)
                    }
                }
            }
            val line = View(context)
            line.setBackgroundColor(ColorProvider.lineColor)
            containerLinearLayout.addView(
                line,
                ParamsProvider.Linear.getParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    SizeProvider.dpToPx(2)
                )
            )
        }




        zXingScannerView = ZXingScannerView(context)
        zXingScannerView.setAspectTolerance(0.5f)
        containerLinearLayout.addView(
            zXingScannerView,
            ParamsProvider.Linear.matchParent
        )

    }

}