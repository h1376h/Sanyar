package com.sanyar.ui.qrreader

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.sanyar.model.Device
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.ParamsProvider
import com.sanyar.provider.SizeProvider
import com.sanyar.provider.TypeFaceProvider
import com.sanyar.ui.views.CustomTextView

class DeviceListItemView(context: Context) : FrameLayout(context) {

    private lateinit var containerLinearLayout: LinearLayout
    private lateinit var customTextView: CustomTextView

    init {
        initView()
    }

    private fun initView() {

        containerLinearLayout = LinearLayout(context)
        containerLinearLayout.orientation = LinearLayout.VERTICAL
        addView(
            containerLinearLayout,
            ParamsProvider.Linear.defaultParams
        )

        customTextView = CustomTextView(context)
        customTextView.setTextColor(ColorProvider.accentColor)
        customTextView.typeface = TypeFaceProvider.getInstance(context).bold
        customTextView.setPadding(
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding()
        )
        containerLinearLayout.addView(
            customTextView,
            ParamsProvider.Linear.defaultParams
                .gravity(Gravity.START)
        )

        val line = View(context)
        line.setBackgroundColor(ColorProvider.lineColor)
        containerLinearLayout.addView(
            line,
            ParamsProvider.Linear.getParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                SizeProvider.dpToPx(1)
            )
        )

    }

    fun setup(device: Device){
        customTextView.text = device.ssid
    }

}