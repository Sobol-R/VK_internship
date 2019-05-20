package com.Sobol.vkplayer

import android.content.Context
import android.view.MotionEvent
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.GestureDetector
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast


class OnSwipeTouchListener(val context: Context) : OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val gestureDetector = GestureDetector(context, GestureListener(v))
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener(val v: View) : SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        //onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }

        fun onSwipeBottom() {
            Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show()
            v.animate()
                .y(v.height.toFloat())
                .duration = 150
        }
    }
}