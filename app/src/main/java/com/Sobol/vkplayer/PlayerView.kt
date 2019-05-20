package com.Sobol.vkplayer

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout
import android.widget.Toast

class PlayerView(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

    var gestureDetector: GestureDetector? = null
    var gestureListener: GestureListener? = null
    var dY = 0.0f
    val view = this

    init {
        LayoutInflater.from(context).inflate(R.layout.player_view, this, true)
        gestureListener = GestureListener(this)
        gestureDetector = GestureDetector(context, gestureListener)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = gestureDetector?.onTouchEvent(event) ?: false

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dY = this.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.rawY + dY > 0) {
                    this.animate()
                        .y(event.rawY + dY)
                        .duration = 0
                }
            }
        }

        return result
    }

}