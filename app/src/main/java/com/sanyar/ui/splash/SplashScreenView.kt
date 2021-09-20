package com.sanyar.ui.splash

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.sanyar.R
import com.sanyar.animation.CustomAnimation
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.ParamsProvider
import com.sanyar.provider.SizeProvider
import com.sanyar.provider.StringProvider
import com.sanyar.ui.qrreader.QrReaderActivity
import com.sanyar.ui.views.CustomTextView

class SplashScreenView(context: Context) : FrameLayout(context) {

    init {
        initView()
    }

    private lateinit var containerLinearLayout: LinearLayout
    private lateinit var imageView: ImageView
    private lateinit var customTextView: CustomTextView



    private fun initView() {
        setBackgroundColor(Color.WHITE)

        containerLinearLayout = LinearLayout(context)
        containerLinearLayout.gravity = Gravity.CENTER
        containerLinearLayout.orientation = LinearLayout.VERTICAL
        addView(
            containerLinearLayout,
            ParamsProvider.Frame.getFullSizeParams()
        )

        imageView = ImageView(context)
        imageView.setImageResource(R.drawable.logo)
        containerLinearLayout.addView(
            imageView,
            ParamsProvider.Frame.getParams(
                SizeProvider.getDisplayW()!!.div(2),
                SizeProvider.getDisplayW()!!.div(2)
            )!!.gravity(Gravity.CENTER)
        )

        customTextView = CustomTextView(context)
        customTextView.gravity = Gravity.CENTER
        customTextView.setTextColor(ColorProvider.accentColor)
        customTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SizeProvider.getTitleSize().toFloat())
        customTextView.text = StringProvider.sanyar
        containerLinearLayout.addView(
            customTextView,
            ParamsProvider.Linear.defaultParams
                .margin(
                    SizeProvider.getGEneralPadding()
                )
        )

        CustomAnimation.pulse(imageView)

        Handler().postDelayed({
            val intent = QrReaderActivity.newIntent(context)
            context.startActivity(intent)
            (context as AppCompatActivity).finish()
        }, 4000)

    }

}