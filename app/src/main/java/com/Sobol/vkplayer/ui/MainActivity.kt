package com.Sobol.vkplayer.ui

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.provider.DocumentFile
import android.util.Log
import android.widget.RelativeLayout
import com.Sobol.vkplayer.model.AudioModel
import com.Sobol.vkplayer.model.Player
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.DocumentsContract
import android.content.ContentResolver
import android.R
import android.media.MediaPlayer
import android.provider.DocumentsContract.Document.COLUMN_MIME_TYPE
import android.provider.DocumentsContract.Document.COLUMN_MIME_TYPE
import java.io.File
import java.net.URI


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.Sobol.vkplayer.R.layout.activity_main)
        choose_directory_button.setOnClickListener { open() }

        val path = "content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FMusic%2FBig%20Baby%20Tape%20-%20ACAB%20(music7s.live).mp3"
        val mUri = Uri.parse(path)
        val mediaPlayer = MediaPlayer()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, mUri)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun open() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        intent.putExtra("android.content.extra.SHOW_ADVANCED", true)
//        val intent = Intent()
//            .setType("*/*")
//            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "open file"), 111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111) {
            System.out.println(data?.data!!.toString())
            //prepare(data?.data!!)
        }
    }

    fun prepare(uri: Uri) {
        val path = uri.path
        val contentResolver = contentResolver
        val docUri = DocumentsContract.buildDocumentUriUsingTree(
            uri,
            DocumentsContract.getTreeDocumentId(uri)
        )
        val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            uri,
            DocumentsContract.getTreeDocumentId(uri)
        )

        val docCursor = contentResolver.query(
            docUri,
            arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE),
            null,
            null,
            null
        )

        val childCursor = contentResolver.query(
            childrenUri,
            arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME, DocumentsContract.Document.COLUMN_MIME_TYPE),
            null,
            null,
            null
        )

        while (docCursor.moveToNext()) {
            System.out.println(docCursor.getString(0))
        }
        while (childCursor.moveToNext()) {
            System.out.println("doc == " + childCursor.getString(0) + "  mime == " + childCursor.getString(1))
            break
        }


//        Log.d("ur1", uri.toString())
//        val mediaMetadataRetriever = MediaMetadataRetriever()
//        mediaMetadataRetriever.setDataSource(this, uri)
//        val artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
//        val title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
//        val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()
//        val audioModel =  AudioModel(artist, title, duration)
//        val player = Player(this, uri)
//        setPlayerView(audioModel, player, uri)
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
