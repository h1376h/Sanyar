package com.sanyar.ui.webview

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import com.sanyar.R
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.ParamsProvider
import com.sanyar.provider.SizeProvider

class RecordCaptureView(context: Context?) : LinearLayout(context) {

    lateinit var captureImageView: ImageView
    lateinit var recordImageView: ImageView

    init {
        initView()
    }

    private fun initView() {

        setBackgroundColor(ColorProvider.opacityDark)

        captureImageView = ImageView(context)
        captureImageView.setImageResource(R.drawable.ic_baseline_photo_camera_24)
        addView(
            captureImageView,
            ParamsProvider.Linear.getWrapContentParams()!!
                .margin(SizeProvider.getGEneralPadding())
        )

        recordImageView = ImageView(context)
        recordImageView.setImageResource(R.drawable.ic_baseline_videocam_24)
        addView(
            recordImageView,
            ParamsProvider.Linear.getWrapContentParams()!!
                .margin(SizeProvider.getGEneralPadding())
        )

    }

}