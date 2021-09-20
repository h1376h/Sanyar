package com.sanyar

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sanyar.provider.StringProvider
import com.sanyar.ui.exo.ExoActivity
import com.sanyar.ui.mk.MkPlayerActivity
import com.sanyar.ui.splash.SplashActivity
import com.sanyar.ui.vlc.VLCActivity
import com.sanyar.ui.webview.WebViewActivity


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CAMERA_SCAN_QR: Int = 1
    private lateinit var buttonExo: Button
    private lateinit var buttonVLC: Button
    private lateinit var buttonMK: Button
    private lateinit var buttonWeb: Button
//    val url = "https://s-v4.tamasha.com/statics/videos_file/0c/e0/7NyB8_0ce0bf0a5fc1210275eaa8b5d47b1957127bb648_n_360.mp4"

    val url = "http://192.168.2.1:8090/?action=stream"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            openSplash()
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA),
                        PERMISSION_REQUEST_CAMERA_SCAN_QR
                    )
                }else{
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_CAMERA_SCAN_QR
                    )
                }
            }
        }

//        setContentView(R.layout.activity_main)
//
//        buttonExo = findViewById(R.id.btn_exo)
//        buttonVLC = findViewById(R.id.btn_vlc)
//        buttonMK = findViewById(R.id.btn_mk)
//        buttonWeb = findViewById(R.id.btn_web)
//
//
//        buttonExo.setOnClickListener {
//            val intent = ExoActivity.newIntent(this, url)
//            startActivity(intent)
//        }
//        buttonVLC.setOnClickListener {
//            val intent = VLCActivity.newIntent(this, url)
//            startActivity(intent)
//        }
//        buttonMK.setOnClickListener {
//            val intent = MkPlayerActivity.newIntent(this, url)
//            startActivity(intent)
//        }
//        buttonWeb.setOnClickListener {
//            val intent = WebViewActivity.newIntent(this, url)
//            startActivity(intent)
//        }
    }

    private fun openSplash() {
        val intent = SplashActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA_SCAN_QR) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.CAMERA) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        openSplash()
                    } else {
                       Toast.makeText(this, StringProvider.persmisionDenied, Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }

}