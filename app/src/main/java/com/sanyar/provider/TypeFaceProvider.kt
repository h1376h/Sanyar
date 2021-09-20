package com.sanyar.provider

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.sanyar.R

class TypeFaceProvider private constructor(val context: Context) {
    var regular: Typeface? = null
    var bold: Typeface? = null
    var semiBold: Typeface? = null

    private fun getTypeFace(resourceName: String, fallbackTypeFace: Typeface): Typeface? {
        var typeface: Typeface? = null
        try {
//            typeface = NZResourceFont.getTypefaceFromResourceIfReady(resourceName)
        } catch (e: Exception) {
        }
        if (typeface == null) {
            typeface = fallbackTypeFace
        }
        return typeface
    }

    companion object {
        var instance: TypeFaceProvider? = null

        fun getInstance(context: Context): TypeFaceProvider {
            if (instance == null) {
                instance = TypeFaceProvider(context)
            }
            return instance!!

        }

    }

    init {
        regular = ResourcesCompat.getFont(context, R.font.regular)
        bold = ResourcesCompat.getFont(context, R.font.bold)
        semiBold = ResourcesCompat.getFont(context, R.font.semibold)
    }
}