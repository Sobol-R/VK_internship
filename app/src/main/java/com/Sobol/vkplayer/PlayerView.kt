package com.Sobol.vkplayer

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout

class PlayerView(context: Context?, attrs: AttributeSet?) :
    RelativeLayout(context, attrs),
    View.OnTouchListener {

    var gestureDetector: GestureDetector? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.player_view, this, true)
        gestureDetector = GestureDetector(context, GestureListener(this))
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        System.out.println("onInterceptTouchEvent")
        return gestureDetector?.onTouchEvent(ev) ?: false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        System.out.println("onTouch")
        return gestureDetector?.onTouchEvent(event) ?: false
    }

}