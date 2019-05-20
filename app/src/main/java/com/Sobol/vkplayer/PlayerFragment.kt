package com.Sobol.vkplayer

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.fragment_player.*
import android.widget.LinearLayout
import android.widget.RelativeLayout

class PlayerFragment : Fragment() {
    var dY: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        song_name.isSelected = true
        setImgSize()
        setSeekBar()
        //changeFragment(R.id.compositions_images, CompositionsImagesFragment())

        if (getScreenHeight() > getScreenWidth())
            //setPortraitScreen()
        else
            setLanscapeScreen()

        view.bringToFront()
        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dY = v!!.y - event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (event.rawY + dY > 0) {
                            v!!.animate()
                                .y(event.rawY + dY)
                                .duration = 0
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (v!!.y >= v.height/5) {
                            v.animate()
                                .setListener(object : Animator.AnimatorListener {
                                    override fun onAnimationRepeat(animation: Animator?) {
                                    }

                                    override fun onAnimationEnd(animation: Animator?) {
                                        fragmentManager?.popBackStack()
                                    }

                                    override fun onAnimationCancel(animation: Animator?) {
                                    }

                                    override fun onAnimationStart(animation: Animator?) {
                                    }
                                })
                                .y(v.height.toFloat())
                                .duration = 150
                        } else {
                            v.animate()
                                .y(0f)
                                .duration = 150
                        }
                    }
                }

                return true
            }
        })
        //anim()
    }

    fun setSeekBar() {
        seek_bar.progressDrawable.setColorFilter(ContextCompat.getColor(context!!, R.color.colorPrimaryLight), PorterDuff.Mode.SRC_IN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            seek_bar.thumb.setColorFilter(ContextCompat.getColor(context!!, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN)
        }
    }

    fun setImgSize() {
        val value: Int
        val width = getScreenWidth()
        val height = getScreenHeight()
        value = if (width <= height) width else height
        val size: Int = (value / 1.5).toInt()
        val layoutParams = RelativeLayout.LayoutParams(
            size, size
        )
        val margin = convertDpToPixel(15, context!!)
        layoutParams.setMargins(0, margin, 0, margin)
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        current_audio_img.layoutParams = layoutParams
    }

    fun setLanscapeScreen() {
        player_cont.orientation = LinearLayout.HORIZONTAL

        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayoutParams.weight = 1f
        compositions_images.layoutParams = linearLayoutParams

        linearLayoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        player.layoutParams = linearLayoutParams
    }

     fun anim() {
        val valueAnimator = ValueAnimator.ofFloat(getScreenHeight().toFloat(), 0f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            content.translationY = value
        }
        valueAnimator.interpolator = AccelerateInterpolator(0.3f)
        valueAnimator.duration = 200

        valueAnimator.start()
    }

    fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getScreenWidth(): Int {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun convertDpToPixel(dp: Int, context: Context): Int {
        return dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun changeFragment(frame: Int, fragment: Fragment) {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
