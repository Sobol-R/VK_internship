package com.Sobol.vkplayer.ui


import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import com.Sobol.vkplayer.R
import kotlinx.android.synthetic.main.fragment_mini_player.*

class MiniPlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mini_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        anim()
        cont.setOnClickListener { changeFragment() }
    }

    fun changeFragment() {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        //transaction.replace(R.id.player_content, PlayerFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun anim() {
        val valueAnimator = ValueAnimator.ofFloat(getScreenHeight().toFloat(), 0f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            cont.translationY = value
        }

        valueAnimator.interpolator = AccelerateInterpolator(0.3f)
        valueAnimator.duration = 225

        valueAnimator.start()
    }

    fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
