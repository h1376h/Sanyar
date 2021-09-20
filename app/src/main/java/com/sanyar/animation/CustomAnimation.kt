package com.sanyar.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.animation.PathInterpolatorCompat
import kotlin.math.round


object CustomAnimation {
    lateinit var animationSet: AnimatorSet
    var duration = 1000L
    var durationLogin = 300L

    fun setAlphaAnimation(view: View, form: Float, to: Float) {
        view.visibility = VISIBLE
        view.alpha = form
        view.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (to != 0f) {
                    view.visibility = VISIBLE
                } else {
                    view.visibility = GONE
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                view.visibility = VISIBLE
            }
        }).alpha(to).setDuration(500L).start()
    }

    fun setHideAnimation(view: View, isGone: Boolean) {
        view.alpha = 1f
        view.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (isGone)
                    view.visibility = GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        }).alpha(0f).setDuration(100).start()
    }


    fun moveAnimation(view: View, y: Float, byY: Float, isVisible: Boolean) {
        moveAnimation(view, y, byY, 200, isVisible)

    }
    fun moveAnimation(view: View, y: Float, byY: Float, duration: Long, isVisible: Boolean) {

        var animation = TranslateAnimation(
            0.0f, 0.0f,
            y, byY
        )
        animation!!.duration = duration // animation duration
        view.startAnimation(animation)
        animation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                view.y = byY

                if (!isVisible) {
                    view.visibility = GONE
                }

            }

            override fun onAnimationStart(animation: Animation?) {
                if (isVisible)
                    view.visibility = LinearLayout.VISIBLE

            }

        })

    }



    fun chengeViewSize(direction: Long, view: View) {
        animationSet = AnimatorSet()
        animationSet.setDuration(direction)
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "scaleX", 3f, 1f),
            ObjectAnimator.ofFloat(view, "scaleY", 3f, 1f),
            ObjectAnimator.ofFloat(view, "alpha", 3f, 1f)
        )

        animationSet.start()
//


    }

    fun bounceInAnimation(view: View) {
        animationSet = AnimatorSet()
        animationSet.duration = duration
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f, 1f, 1f)
        )
        animationSet.start()
    }

    fun fadeInAnimation(view: View) {
        Handler().postDelayed({

            view.visibility = VISIBLE
            animationSet = AnimatorSet()
            animationSet.duration = durationLogin
            animationSet.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(view, "translationY", -view.getHeight() / 4f, 0f)
            )
            animationSet.start()
        }, 500)
    }

    fun roberBandAnimator(view: View) {
        view.visibility = VISIBLE
        animationSet = AnimatorSet()
        animationSet.duration = durationLogin
        val interpolator = PathInterpolatorCompat.create(
            0.5f, -0.5f,
            0.5f, 1.5f
        )
        animationSet.interpolator = interpolator
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.25f, 0.75f, 1.15f, 1f),
            ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.75f, 1.25f, 0.85f, 1f)
        )
        animationSet.start()
    }

    fun pulse(view: View) {
        view.visibility = VISIBLE
        animationSet = AnimatorSet()
        animationSet.duration = 6000
        val pulsuAmount = 1.02f
//        val interpolator = PathInterpolatorCompat.create(
//            0.4f, -0.5f,
//            0.5f, 1.5f
//        )
//        animationSet.interpolator = interpolator
        val animY = ObjectAnimator.ofFloat(view, "scaleY", 1f, pulsuAmount, 1f, pulsuAmount, 1f, pulsuAmount, 1f)
        animY.repeatCount = Animation.INFINITE
        val animX = ObjectAnimator.ofFloat(view, "scaleX", 1f, pulsuAmount, 1f, pulsuAmount, 1f, pulsuAmount, 1f)
        animX.repeatCount = Animation.INFINITE
        animationSet.playTogether(
            animX,
            animY
        )
        animationSet.start()
    }

    fun rotateInAnimator(view: View) {
        animationSet = AnimatorSet()
        animationSet.duration = durationLogin * 1
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
            ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(view, "rotation", 0f, 720f)
        )
        animationSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animationSet.start()
    }

    fun SlideUpAnimator(view: View, dur: Long) {
        animationSet = AnimatorSet()
        animationSet.duration = dur * 1
        val distance: Int = view.rootView.height - view.getTop()
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(view, "translationY", distance.toFloat(), 0f)
        )
        animationSet.start()
    }

    fun SlideinAnimator(view: View, dur: Long) {
        animationSet = AnimatorSet()
        animationSet.duration = dur * 1

        val distance: Int = view.rootView.width - view.getLeft()
        animationSet.playTogether(
            ObjectAnimator.ofFloat(view, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(view, "translationX", distance.toFloat(), 0f)
        )
        animationSet.start()
    }

    fun initAnimationPadding(defultNumber: Int, byNumber: Int, view: View) {


        var anim = ValueAnimator.ofInt(defultNumber!!, byNumber)
        anim.addUpdateListener {

            var a = it!!.getAnimatedValue() as Int
            view.setPadding(a, a, a, a)


        }


        anim.setDuration(1000)

        anim.start()


    }


}