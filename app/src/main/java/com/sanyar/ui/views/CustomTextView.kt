package com.sanyar.ui.views

import android.content.Context
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.sanyar.provider.ColorProvider
import com.sanyar.provider.SizeProvider

class CustomTextView(context: Context) : AppCompatTextView(context) {

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, SizeProvider.getGeneralTextSize().toFloat())
        setTextColor(ColorProvider.textColor)
    }

}