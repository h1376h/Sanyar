package com.sanyar.ui.exo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ExoActivity : AppCompatActivity() {

    companion object{
        val KEY_URL = "url"
        fun newIntent(context: Context, url: String): Intent{
            val intent = Intent(context, ExoActivity::class.java)
            intent.putExtra(KEY_URL, url)
            return intent
        }
    }

    private lateinit var exoView: ExoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(KEY_URL)
        exoView = ExoView(this, Uri.parse(url))
        setContentView(exoView)
    }
}