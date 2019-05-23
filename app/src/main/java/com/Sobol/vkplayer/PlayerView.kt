package com.Sobol.vkplayer

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.Sobol.vkplayer.model.AudioModel
import com.Sobol.vkplayer.model.Player
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.player_view.view.*

class PlayerView(context: Context?, val audioModel: AudioModel, val mediaPlayer: Player) :
    RelativeLayout(context) {

    var gestureDetector: GestureDetector? = null
    var gestureListener: GestureListener? = null
    var dY = 0.0f
    var activity: Activity? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.player_view, this, true)
        activity = context as Activity
        song_name.isSelected = true
        mini_player_title.isSelected = true
        setImgSize()
        setSeekBar()
        setData()
        setPlayerUpdate()
        switcher.setOnClickListener { updateSwitcher() }
        mini_player_switcher.setOnClickListener { updateSwitcher() }
        gestureListener = GestureListener(this, cont, full_player)
        gestureDetector = GestureDetector(context, gestureListener)
        if (getScreenHeight() < getScreenWidth()) {
            setLanscapeScreen()
        }
    }

    fun updateSwitcher() {
        if (mediaPlayer.mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            switcher_image.setImageResource(R.drawable.ic_play_48)
            mini_player_switcher.setImageResource(R.drawable.ic_play_48)
        }
        else {
            mediaPlayer.play()
            switcher_image.setImageResource(R.drawable.ic_pause_48)
            mini_player_switcher.setImageResource(R.drawable.ic_pause_28)
        }
    }

    fun setPlayerUpdate() {
        mediaPlayer.updatePlayerView(listened_time, left_time, mini_player_audio_timing, seek_bar)
    }

    fun setData() {
        song_name.text = audioModel.title
        singer_name.text = audioModel.artist
        mini_player_title.text = audioModel.title
        val time : Float = audioModel.duration!!.toFloat() / 1000 / 60
        val min : Int = time.toInt()
        val sec : Int = (60 * (time - min)).toInt()
        val timeLeft = "-$min:$sec"
        left_time.text = timeLeft
        mini_player_audio_timing.text = timeLeft
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val fullHeight = full_player.height
        val result = gestureDetector?.onTouchEvent(event) ?: false
        var startPos = 0
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dY = this.y - event.rawY
                startPos = this.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {

            }
        }

        return result
    }

    fun convertDpToPixel(dp: Int, context: Context): Int {
        return dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
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
}