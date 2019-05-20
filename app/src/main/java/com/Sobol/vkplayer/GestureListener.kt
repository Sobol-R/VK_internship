package com.Sobol.vkplayer

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class GestureListener(val v: View) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 1000
    private val SWIPE_VELOCITY_THRESHOLD = 1000
    var result = false

    override fun onDown(e: MotionEvent): Boolean {
        //System.out.println("onDown")
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
           swipeBottom()
            result = true
//            val diffY = e2.y - e1.y
//            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                if (diffY > 0) {
//                    onSwipeBottom()
//                } else {
//                    //onSwipeTop()
//                }
//                result = true
//            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }

    fun swipeBottom() {
        Toast.makeText(v.context, "bottom", Toast.LENGTH_SHORT).show()
        v.animate()
            .y(v.height.toFloat())
            .duration = 50
    }
}