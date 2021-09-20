package com.sanyar.ui.webview

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.sanyar.provider.ParamsProvider


class WebViewPlayer(context: Context, val url: String) : FrameLayout(context) {

    private var visible: Boolean = true
    lateinit var webView: WebView
    lateinit var recordCaptureView: RecordCaptureView

    init {
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        webView = WebView(context)
        webView.loadUrl(url)
        webView.requestLayout()
        webView.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//        webSettings.allowFileAccess = true
//        webSettings.setAppCacheEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        addView(webView, ParamsProvider.Frame.getFullSizeParams())

        recordCaptureView = RecordCaptureView(context)
        addView(
            recordCaptureView,
            ParamsProvider.Frame.getWrapContentParams()?.gravity(Gravity.BOTTOM or Gravity.LEFT)
        )

        setOnClickListener {
            if (visible){
                recordCaptureView.alpha = 1f
                recordCaptureView.animate().setDuration(300).alpha(0f).setListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        recordCaptureView.visibility = GONE
                        visible = !visible
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                }).start()
            }else{
                recordCaptureView.visibility = VISIBLE
                recordCaptureView.alpha = 0f
                recordCaptureView.animate().alpha(1f).setDuration(300).setListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        visible = !visible
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                }).start()
            }
        }

    }



}