package com.sanyar.ui.exo

import android.content.Context
import android.net.Uri
import android.widget.FrameLayout


class ExoView(context: Context, val uri: Uri?) : FrameLayout(context) {

//    private lateinit var playerView: PlayerView
//    private lateinit var player: SimpleExoPlayer
//
//    init {
//        initView()
//    }
//
//    private fun initView() {
//        playerView = PlayerView(context)
//        addView(
//            playerView,
//            LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        )
//        play(uri)
//        player.play()
//    }
//
//
//    fun play(uri: Uri?){
//        initializePlayer()
//        preparePlayer(uri!!)
//        hideSystemUi()
//    }
//
//    private fun initializePlayer() {
//        player = SimpleExoPlayer.Builder(
//            context,
//            DefaultRenderersFactory(context),
//            DefaultExtractorsFactory()
//        ).build()
//        playerView.player = player
//        player.playWhenReady = true
//    }
//
//
//    private fun preparePlayer(uri: Uri){
//        val mediaSource = MediaSourceBuilder().build(uri)
//        player?.setMediaSource(mediaSource, true)
//    }
//
//    //Overloaded function to build MediaSource for whole playlist and prepare player
//    private fun preparePlayer(uriList: Array<Uri>){
//        val mediaSource = MediaSourceBuilder().build(uriList)
//        player?.setMediaSource(mediaSource, true)
//    }
//
//    private fun hideSystemUi() {
//        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
//    }
//
//    class MediaSourceBuilder {
//        val TAG = "MediaSourceBuilder>>>"
//        //Build various MediaSource depending upon the type of Media for a given video/audio uri
//        fun build(uri: Uri): MediaSource {
//
//            val lastPath = uri.lastPathSegment?:""
//            Log.d(TAG, "build: last path $lastPath")
//            val defaultHttpDataSourceFactory = DefaultHttpDataSourceFactory(null)
//
//            if(lastPath.contains("mp3") || lastPath.contains("mp4")){
//                Log.d(TAG, "build: containts")
//                return ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(uri))
//
//
//            }else if(lastPath.contains("m3u8")){
//
//                return HlsMediaSource.Factory(defaultHttpDataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(uri))
//
//            }else{
//                val dashChunkSourceFactory = DefaultDashChunkSource.Factory(
//                    defaultHttpDataSourceFactory
//                )
//                return DashMediaSource.Factory(dashChunkSourceFactory, defaultHttpDataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(uri))
//
//            }
//        }
//
//
//        //Overloaded function to Build various MediaSource for whole playlist of video/audio uri
//        fun build(uriList: Array<Uri>) : MediaSource{
//            val playlistMediaSource = ConcatenatingMediaSource()
//            uriList.forEach { playlistMediaSource.addMediaSource(build(it)) }
//            return playlistMediaSource
//        }
//
//    }

}