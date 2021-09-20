package com.sanyar.ui.qrreader

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.ParamsProvider
import com.sanyar.provider.SizeProvider
import com.sanyar.ui.views.CustomTextView

class PlayerChooserView(context: Context, val presenter: PlayerChooserPresenter) : FrameLayout(context) {


    interface PlayerChooserPresenter{
        fun webCalled()
    }

    private lateinit var containerLinearLayout: LinearLayout
    private lateinit var webViewText: CustomTextView
    private lateinit var line: View
    private lateinit var mkCustomTextView: CustomTextView

    init {
        initView()
    }

    private fun initView() {

        containerLinearLayout = LinearLayout(context)
        containerLinearLayout.orientation = LinearLayout.VERTICAL
        addView(containerLinearLayout, ParamsProvider.Frame.defaultParams)

        webViewText = CustomTextView(context)
        webViewText.setPadding(
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding(),
            SizeProvider.getGEneralPadding()
        )
        webViewText.text = "Web view"
        webViewText.gravity = Gravity.CENTER
        containerLinearLayout.addView(
            webViewText,
            ParamsProvider.Linear.defaultParams
                .margins(
                    0,
                    0,
                    0,
                    0
                )
        )

        line = View(context)
        line.setBackgroundColor(ColorProvider.gray)
        containerLinearLayout.addView(line, ParamsProvider.Linear.getParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeProvider.dpToPx(1)))

//        mkCustomTextView = CustomTextView(context)
//        mkCustomTextView.setPadding(
//            SizeProvider.getGEneralPadding(),
//            SizeProvider.getGEneralPadding(),
//            SizeProvider.getGEneralPadding(),
//            SizeProvider.getGEneralPadding()
//        )
//        mkCustomTextView.text = "Mk player"
//        mkCustomTextView.gravity = Gravity.CENTER
//        containerLinearLayout.addView(
//            mkCustomTextView,
//            ParamsProvider.Linear.defaultParams
//                .margins(
//                    0,
//                    0,
//                    0,
//                    0
//                )
//        )


        webViewText.setOnClickListener {
            presenter.webCalled()
        }

    }

}