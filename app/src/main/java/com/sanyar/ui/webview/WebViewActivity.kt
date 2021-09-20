package com.sanyar.ui.webview

import android.Manifest
import android.animation.Animator
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.AudioManager
import android.media.MediaActionSound
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.sanyar.provider.StringProvider
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class WebViewActivity : AppCompatActivity() {

    val TAG = "WebViewActivity>>>"
    companion object{
        val KEY_URL = "url"
        fun newIntent(context: Context, url: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            return intent
        }
    }


    private val REQUEST_CODE = 1000
    private val REQUEST_PERMISSION_RECORD = 1001
    private val REQUEST_PERMISSION_CAPTURE = 1001
    private val oritentations = SparseIntArray()

    private var mediaProjectionManager: MediaProjectionManager? = null
    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private lateinit var mediaProjectionCallBack: MediaProjectionCallBack
    private lateinit var mediaRecorder: MediaRecorder


    private var screenDensity: Int = 0
    private val DISPLAY_WIDTH = 720
    private val DISPLAY_HEIGHT = 1280



    private lateinit var webViewPlayer: WebViewPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        webViewPlayer = WebViewPlayer(this, intent.getStringExtra(KEY_URL)!!)
//        webViewPlayer = WebViewPlayer(this, "https://www.google.com/")
        setContentView(webViewPlayer)

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics);
        screenDensity = metrics.densityDpi

        mediaRecorder = MediaRecorder()
        mediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager


        webViewPlayer.recordCaptureView.recordImageView.setOnClickListener {
            startRecording()
        }
        webViewPlayer.recordCaptureView.captureImageView.setOnClickListener {
            takeScreenShot(webViewPlayer.webView)
        }


    }

    private fun startRecording() {
        if (permissionGranted()){
            recordScreen()
        }else{
            requestPermission(REQUEST_PERMISSION_RECORD)
        }
    }

    private fun takeScreenShot(view: View){
        if (permissionGranted()){
            val bitmap = screenShot(view)

            saveImage(
                bitmap,
                this,
                StringProvider.name
            )
            val audio = getSystemService(AUDIO_SERVICE) as AudioManager
            when (audio.ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> {
                    val sound = MediaActionSound()
                    sound.play(MediaActionSound.SHUTTER_CLICK)
                }
                AudioManager.RINGER_MODE_SILENT -> {
                }
                AudioManager.RINGER_MODE_VIBRATE -> {
                }
            }

            view.animate().alpha(0f).setDuration(50).scaleX(0.9f).scaleY(0.9f).setListener(object :
                Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    view.animate().alpha(1f).setDuration(50).setStartDelay(50).scaleX(1f).scaleY(1f).start()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationRepeat(animation: Animator?) {

                }

            }).start()

        }else{
            requestPermission(REQUEST_PERMISSION_CAPTURE)
        }
    }

    private fun requestPermission(code: Int) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            code
        )
    }

    private fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun recordScreen() {
        if (mediaProjection == null){
            initRecorder(this)
            startActivityForResult(
                mediaProjectionManager?.createScreenCaptureIntent(),
                REQUEST_CODE
            )
            return
        }else{
            if (virtualDisplay == null){
                virtualDisplay = createVirtualDisplay()
                mediaRecorder.start()
            }else {
                stopRecordingScreen()
            }
        }
    }

    private fun createVirtualDisplay(): VirtualDisplay {
        return mediaProjection!!.createVirtualDisplay(
            "WebActivity",
            DISPLAY_WIDTH,
            DISPLAY_HEIGHT,
            screenDensity,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder.surface,
            null,
            null
        )
    }

    private fun initRecorder(context: Context) {
        var videoUri: String
        try {
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.RELATIVE_PATH, StringProvider.name + separator + "Records")
                values.put(MediaStore.Images.Media.IS_PENDING, true)
                // RELATIVE_PATH and IS_PENDING are introduced in API 29.

                val uri: Uri? = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                videoUri = uri.toString()
            } else {
                val directory = File(
                    Environment.getExternalStorageDirectory().toString() + separator + StringProvider.name + separator + "Records"
                )
                // getExternalStorageDirectory is deprecated in API 29

                if (!directory.exists()) {
                    directory.mkdirs()
                }
                val fileName = System.currentTimeMillis().toString() + ".mp4"
                val file = File(directory, fileName)
                videoUri = file.path
            }
//            val videoUri =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() +
//                        StringBuilder("/").append(
//                            SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(
//                                Date()
//                            )
//                        ).append(".mp4").toString()
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setOutputFile(videoUri)
            mediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.setVideoEncodingBitRate(512 * 1000)
            mediaRecorder.setVideoFrameRate(30)

            mediaRecorder.prepare()

        }catch (ex: Exception){
            Log.d(TAG, "initRecorder: ex : " + ex)
        }
    }
    private fun stopRecordingScreen(){
        if (virtualDisplay == null) return
        virtualDisplay!!.release()
        destroyMediaProjeciton()
    }

    private fun destroyMediaProjeciton() {
        if (mediaProjection != null){
            mediaProjection!!.stop()
            mediaProjection = null
        }
    }

    private fun screenShot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName + "/" + "Images")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
        } else {
            val directory = File(
                Environment.getExternalStorageDirectory().toString() + separator + folderName + separator + "Images"
            )
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DURATION, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        }
        Toast.makeText(context, StringProvider.saved, Toast.LENGTH_SHORT).show()
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                mediaProjectionCallBack = MediaProjectionCallBack()
                mediaProjection = mediaProjectionManager!!.getMediaProjection(resultCode, data!!)
//                mediaProjection.registerCallback(mediaProjectionCallBack, null)
                virtualDisplay = createVirtualDisplay()
                mediaRecorder.start()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_RECORD){
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_GRANTED){
                    startRecording()
                }else{
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show()
                }
            }
        }else if (requestCode == REQUEST_PERMISSION_CAPTURE){
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_GRANTED){
                    takeScreenShot(webViewPlayer.webView)
                }else{
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopRecordingScreen()
    }

    override fun onPause() {
        super.onPause()
    }


    class MediaProjectionCallBack: MediaProjection.Callback() {
        override fun onStop() {
            super.onStop()
        }
    }
}


