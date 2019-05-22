package com.Sobol.vkplayer.model

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import android.app.Activity


class Player(context: Context, uri: Uri) {

    val mediaPlayer = MediaPlayer()
    val activity: Activity = context as Activity

    init {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepare()
    }

    fun play() {
        mediaPlayer.start()
    }

    fun stop() {
        mediaPlayer.stop()
    }

    fun updatePlayerView(timeListened: TextView, timeLeft: TextView,
                         miniPlayerTime: TextView, seekBar: SeekBar) {
        val fullTime : Float = mediaPlayer.duration.toFloat() / 1000 / 60
        var min: Int
        var sec: Int
        var currentTime: Float
        var minListened: Int
        var secListened: Int
        var minLeft: Int
        var secLeft: Int
        var textTimeListened: String
        var textTimeLeft: String
        var currentPos: Int
        seekBar.max = mediaPlayer.duration
        play()
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    if (mediaPlayer.isPlaying) {
                        currentPos = mediaPlayer.currentPosition
                        currentTime = currentPos.toFloat() / 1000 / 60
                        min = fullTime.toInt()
                        sec = (60 * (fullTime - min)).toInt()
                        minListened = currentTime.toInt()
                        secListened = (60 * (currentTime - minListened)).toInt()
                        minLeft = min - minListened
                        secLeft = sec - secListened
                        textTimeListened = "$minListened:$secListened"
                        textTimeLeft = "-$minLeft:$secLeft"
                        timeListened.text = textTimeListened
                        timeLeft.text = textTimeLeft
                        miniPlayerTime.text = textTimeLeft
                        seekBar.progress = currentPos
                    }
                }
            }
        }, 0, 1000)
    }

    fun changeCurrentPosition(position: Int) {

    }

}