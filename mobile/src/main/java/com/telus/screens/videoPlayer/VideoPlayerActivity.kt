package com.telus.screens.videoPlayer

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import android.widget.VideoView
import com.telus.R
import timber.log.Timber

class VideoPlayerActivity : AppCompatActivity() {

    lateinit var mVideoView: VideoView
    lateinit var mMediaController: MediaController
    lateinit var mProgressDialog: ProgressDialog

    private var position = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        mMediaController = MediaController(this)
        mVideoView = findViewById(R.id.videoView)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Telus Video!")
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()

        try {
            //set the media controller in the VideoView
            mVideoView.setMediaController(mMediaController)
            //set the uri of the video to be played
            mVideoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.video))
        }
        catch (e:Exception) {
            Timber.e("Error ${e.message}")
            e.printStackTrace()
        }

        mVideoView.requestFocus()
        mVideoView.setOnPreparedListener { mp ->
            run {
                mProgressDialog.dismiss()
                mVideoView.seekTo(position)
                if (position == 0) {
                    mVideoView.start()
                } else {
                    mVideoView.pause()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("Position", mVideoView.currentPosition)
        mVideoView.pause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        position = savedInstanceState?.getInt("Position") ?: 0
        mVideoView.seekTo(position);
    }
}