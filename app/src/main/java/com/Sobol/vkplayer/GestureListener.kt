package com.Sobol.vkplayer

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast

class GestureListener(val v: View, val miniPlayerView: View, val fullPlayerView: View) : GestureDetector.SimpleOnGestureListener() {

    var result = false
    var fullHeight: Int = 0

    override fun onDown(e: MotionEvent): Boolean {
        //System.out.println("onDown")
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
                if (diffY > 0) {
                    swipeBottom()
                } else {
                   swipeTop()
                }
                result = true
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }

    fun swipeBottom() {
        fullHeight = v.height
        if (v.y < miniPlayerView.height) {
            val animation = ValueAnimator.ofInt(fullHeight, miniPlayerView.height)
            animation.duration = 300
            animation.addUpdateListener {
                val value = it.animatedValue as Int
                v.layoutParams.height = value
                v.requestLayout()
                v.y = (fullHeight - value).toFloat()
                miniPlayerView.alpha = (v.y / ((fullHeight - miniPlayerView.height) / 100)) / 100f
                fullPlayerView.alpha = 1 - (v.y / ((fullHeight - miniPlayerView.height) / 100)) / 100f
            }
            animation.start()
        }
    }

    fun swipeTop() {
        if (v.y > 0) {
            val animation = ValueAnimator.ofInt(miniPlayerView.height, fullHeight)
            animation.duration = 300
            animation.addUpdateListener {
                val value = it.animatedValue as Int
                v.layoutParams.height = value
                v.requestLayout()
                v.y = (fullHeight - value).toFloat()
                miniPlayerView.alpha = (v.y / ((fullHeight - miniPlayerView.height) / 100)) / 100f
                fullPlayerView.alpha = 1 - (v.y / ((fullHeight - miniPlayerView.height) / 100)) / 100f
            }
            animation.start()
        }
    }

    fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        val activity = v.context as Activity
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}