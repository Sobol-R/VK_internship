package com.Sobol.vkplayer.ui

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import com.Sobol.vkplayer.R
import com.Sobol.vkplayer.model.AudioModel
import com.Sobol.vkplayer.model.Player
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        choose_directory_button.setOnClickListener { open() }
    }

    fun open() {
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "open file"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111) {
            System.out.println(data?.data.toString())
            prepare(data?.data!!)
        }
    }

    fun prepare(uri: Uri) {
        Log.d("ur1", uri.toString())
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(this, uri)
        val artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()
        val audioModel =  AudioModel(artist, title, duration)
        val player = Player(this, uri)
        setPlayerView(audioModel, player, uri)
    }

    fun setPlayerView(audioModel: AudioModel, player: Player, uri: Uri) {
        val playerView = PlayerView(this, audioModel, player, uri)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
        )
        playerView.layoutParams = params
        main_content.addView(playerView)
    }
}
