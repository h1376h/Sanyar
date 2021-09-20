package com.sanyar.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanyar.ui.qrreader.QrReaderActivity

class SplashActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SplashActivity::class.java)
            return intent
        }
    }
    private lateinit var splashScreenView: SplashScreenView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreenView = SplashScreenView(this)
        setContentView(splashScreenView)

    }
}