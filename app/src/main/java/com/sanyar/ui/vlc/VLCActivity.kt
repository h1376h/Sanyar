package com.sanyar.ui.vlc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class VLCActivity : AppCompatActivity() {
//    private lateinit var mMediaPlayer: MediaPlayer
//    private lateinit var mLibVLC: LibVLC
//    //    private lateinit var viewer: MjpegView
//    var url = ""
//    val TAG = "MainActivity>>>"
//    private lateinit var frameLayout: FrameLayout
//    private lateinit var vlcVideoLayout: VLCVideoLayout


    companion object{
        val KEY_URL = "url"
        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, VLCActivity::class.java)
            intent.putExtra(KEY_URL, url)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        frameLayout = FrameLayout(this)
//        setContentView(frameLayout)
//        url = intent.getStringExtra(KEY_URL)!!
//        Log.d(TAG, "onCreate: url : $url")
//        vlcVideoLayout = VLCVideoLayout(this)
//        frameLayout.addView(
//                vlcVideoLayout,
//                FrameLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                )
//        )
//
//        mLibVLC = LibVLC(this, ArrayList<String>().apply {
//            add("--no-drop-late-frames")
//            add("--no-skip-frames")
//            add("--rtsp-tcp")
//            add("-vvv")
//            add("--file-caching=2000")
//        })
//        mMediaPlayer = MediaPlayer(mLibVLC)
//        mMediaPlayer?.attachViews(vlcVideoLayout, null, false, false)
//
//        try {
//            val httpUrl = url
//            val uri = Uri.parse(httpUrl) // ..whatever you want url...or even file fromm asset
//
//            Media(mLibVLC, uri).apply {
//                setHWDecoderEnabled(true, false);
//                addOption(":network-caching=150");
//                addOption(":clock-jitter=0");
//                addOption(":clock-synchro=0");
//                mMediaPlayer?.media = this
//
//            }.release()
//
//            mMediaPlayer?.play()
//
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }

//    override fun onResume() {
//        super.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//    }
//    override fun onStop() {
//        super.onStop()
//        mMediaPlayer?.stop()
//        mMediaPlayer?.detachViews()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mMediaPlayer?.release()
//        mLibVLC?.release()
//    }
}