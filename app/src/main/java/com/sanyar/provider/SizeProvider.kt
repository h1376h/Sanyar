package com.sanyar.provider

import android.util.TypedValue
import com.sanyar.application.ApplicationContextHolder

object SizeProvider {

    fun getCardRadius(): Int {

        return dpToPx(10).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            ApplicationContextHolder.application?.resources?.displayMetrics
        ).toInt()
    }
    fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            ApplicationContextHolder.application?.resources?.displayMetrics
        ).toInt()
    }

    fun getDisplayW(): Int? {
        return ApplicationContextHolder.application?.resources?.displayMetrics?.widthPixels
    }

    fun getDisplayH(): Int? {
        return ApplicationContextHolder.application?.resources?.displayMetrics?.heightPixels
    }

    fun getGeneralTextSize(): Int {
        return 14
    }

    fun getGEneralPadding(): Int {
        return dpToPx(15)
    }

    fun getTitleSize(): Int {
        return dpToPx(16)
    }

}