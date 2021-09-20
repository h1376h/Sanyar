package com.sanyar.provider

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

object ParamsProvider {

    fun getLinearLayoutParams(width: Int, height: Int): LinearLayout.LayoutParams? {
        return LinearLayout.LayoutParams(width, height)
    }

    fun getHorizontalLineParams(): Linear.Params {
        return Linear.Params(LinearLayout.LayoutParams.MATCH_PARENT, SizeProvider.dpToPx(1))
    }


    object Linear {


        val defaultParams: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val matchParent: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val availableHeightParams: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)

        fun getAvailableWidthParams(): Params? {
            return Params(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        }
        fun getAvailableWidthParams(Hight: Int): Params? {
            return Params(0, Hight, 1f)
        }

        fun getParams(width: Int, height: Int): Params {
            return Params(width, height)
        }


        class Params : LinearLayout.LayoutParams {
            constructor(width: Int, height: Int) : super(width, height) {}
            constructor(width: Int, height: Int, weight: Float) : super(width, height, weight) {}

            fun margin(margin: Int): Params {
                marginEnd = margin
                marginStart = margin
                topMargin = margin
                bottomMargin = margin
                return this
            }

            fun margins(
                marginLeft: Int,
                marginTop: Int,
                marginRight: Int,
                marginBottom: Int
            ): Params {
                marginEnd = marginRight
                marginStart = marginLeft
                topMargin = marginTop
                bottomMargin = marginBottom
                return this
            }

            fun gravity(gravity: Int): Params {
                this.gravity = gravity
                return this
            }

            fun setWeight(w: Int): Params {
                this.weight = w.toFloat()
                return this
            }
        }


        fun getWrapContentParams(): Params? {
            return Params(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }


    }

    object Frame {
        val defaultParams: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        fun getWrapContentParams(): Params? {
            return Params(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        fun getFullSizeParams(): Params? {
            return Params(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        fun getParams(imageSize: Int, imageSize1: Int): Params? {
            return Params(imageSize, imageSize1)
        }

        class Params(width: Int, height: Int) : FrameLayout.LayoutParams(width, height) {
            fun gravity(gravity: Int): Params {
                super.gravity = gravity
                return this
            }


            fun margin(margin: Int): Params {
                marginEnd = margin
                marginStart = margin
                topMargin = margin
                bottomMargin = margin
                return this
            }

            fun margins(
                marginLeft: Int,
                marginTop: Int,
                marginRight: Int,
                marginBottom: Int
            ): Params {
                marginEnd = marginRight
                marginStart = marginLeft
                topMargin = marginTop
                bottomMargin = marginBottom
                return this
            }
        }
    }

    object Relative {
        fun getParams(width: Int, height: Int): RelativeLayout.LayoutParams {
            return RelativeLayout.LayoutParams(width, height)
        }

        val defaultParams: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val matchParams: Params
            get() = Params(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val wrapContentParams: Params
            get() = Params(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        class Params(width: Int, height: Int) : RelativeLayout.LayoutParams(width, height) {
            fun margin(margin: Int): Params {
                marginEnd = margin
                marginStart = margin
                topMargin = margin
                bottomMargin = margin

                return this
            }

            fun margins(
                marginLeft: Int,
                marginTop: Int,
                marginRight: Int,
                marginBottom: Int
            ): Params {
                marginEnd = marginRight
                marginStart = marginLeft
                topMargin = marginTop
                bottomMargin = marginBottom
                return this
            }

            fun alignparentRight(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                return this
            }

            fun alignparentLeft(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                return this
            }

            fun alignparentStart(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_START)
                return this
            }

            fun alignparentEnd(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_END)
                return this
            }

            fun alignparentTop(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_TOP)
                return this
            }

            fun alignparentBottom(): Params {
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                return this
            }

            fun toLeftOf(id: Int): Params {

                addRule(RelativeLayout.LEFT_OF, id)
                return this
            }

            fun toRightOf(id: Int): Params {

                addRule(RelativeLayout.RIGHT_OF, id)
                return this
            }

            fun toEndOf(id: Int): Params {

                addRule(RelativeLayout.END_OF, id)
                return this
            }

            fun toStartOf(id: Int): Params {

                addRule(RelativeLayout.START_OF, id)
                return this
            }

            fun aboveOf(id: Int): Params {

                addRule(RelativeLayout.ABOVE, id)
                return this
            }

            fun below(id: Int): Params {

                addRule(RelativeLayout.BELOW, id)
                return this
            }
  fun baseLine(id: Int): Params {

                addRule(RelativeLayout.ALIGN_BASELINE, id)
                return this
            }

            fun centerVertical(): Params {

                addRule(RelativeLayout.CENTER_VERTICAL)
                return this
            }

            fun centerHorizontal(): Params {

                addRule(RelativeLayout.CENTER_HORIZONTAL)
                return this
            }

            fun BaseLine(id: Int): Params {

                addRule(RelativeLayout.ALIGN_BASELINE, id)
                return this
            }

            fun centerInParent(): Params {

                addRule(RelativeLayout.CENTER_IN_PARENT)
                return this
            }


        }
    }

    object HorizontalScroll {
        val defaultParams: Params
            get() = Params(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        class Params : FrameLayout.LayoutParams {
            constructor(width: Int, height: Int) : super(width, height) {}
            constructor(width: Int, height: Int, gravity: Int) : super(width, height, gravity) {}

            fun margin(margin: Int): Params {
                marginEnd = margin
                marginStart = margin
                topMargin = margin
                bottomMargin = margin
                return this
            }

            fun margins(
                marginLeft: Int,
                marginTop: Int,
                marginRight: Int,
                marginBottom: Int
            ): Params {
                marginEnd = marginRight
                marginStart = marginLeft
                topMargin = marginTop
                bottomMargin = marginBottom
                return this
            }
        }
    }
}