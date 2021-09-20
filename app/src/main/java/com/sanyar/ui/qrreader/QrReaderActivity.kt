package com.sanyar.ui.qrreader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.Result
import com.sanyar.model.Device
import com.sanyar.ui.mk.MkPlayerActivity
import com.sanyar.ui.webview.WebViewActivity
import com.sanyar.util.Prefrences
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*


class QrReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    interface SuccessCallback{
        fun call()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, QrReaderActivity::class.java)
            return intent
        }
    }

    private val REQ_WIFI: Int = 2
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var wifiManager: WifiManager
    val TAG = this.javaClass.name + ">>>"
    private lateinit var qrReaderView: QRReaderView

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var chooserView: PlayerChooserView
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        qrReaderView = QRReaderView(this, Prefrences.getAllDevices(this), object : QRReaderView.Presenter{
            override fun onClick(device: Device) {
                connectToWifi(
                    ssid = device.ssid,
                    pass = device.password,
                    callback = object :SuccessCallback{
                        override fun call() {
                            url = device.link
                            openurl(url)
                        }
                    }
                )
            }
        })
        setContentView(qrReaderView)

//        Handler().postDelayed({
//            connectToWifi("HOSSEIN-LAPTOP", "123456789")
//        }, 1000)


        bottomSheetDialog = BottomSheetDialog(this)
        chooserView = PlayerChooserView(this, object : PlayerChooserView.PlayerChooserPresenter{
            override fun webCalled() {
                openurl(url)
                bottomSheetDialog.dismiss()
            }

        })
        bottomSheetDialog.setContentView(chooserView)

    }

    private fun openurl(url: String) {
        startActivity(WebViewActivity.newIntent(this@QrReaderActivity, url))
    }

    override fun onResume() {
        super.onResume()
        qrReaderView.zXingScannerView.setResultHandler(this)
        qrReaderView.zXingScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrReaderView.zXingScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        val result = rawResult.toString().split("000000")
        if (result.size == 4){
            Prefrences.saveDevice(this, Device(
                id = result[0],
                ssid = result[1],
                password = result[2],
                link = result[3]
            ))
            connectToWifi(result[1], result[2], object :SuccessCallback{
                override fun call() {
                    url = result[3]
//                    bottomSheetDialog.show()
                    openurl(url)
                }
            })
        }

    }

    private fun connectToWifi(ssid: String, pass: String, callback: SuccessCallback) {
        var available = false
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val list = wifiManager.configuredNetworks
            Log.d(TAG, "connectToWifi: " + (list == null))
            if (!list.isNullOrEmpty()){
                for (i in list) {
                    Log.d(TAG, "connectToWifi: " + i.SSID)
                    if (i.SSID != null && i.SSID == "\"" + ssid.toString() + "\"") {
                        Log.d(TAG, "connectToWifi: ssid : ${i.SSID}")
                        wifiManager.disconnect()
                        wifiManager.enableNetwork(i.networkId, true)
                        wifiManager.reconnect()
                        available = true
                        callback.call()
                        return
                    }
                }
            }
        }
        if (!available) {
            Log.d(TAG, "connectToWifi: not available")
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val conf = WifiConfiguration()
                conf.SSID = String.format("\"%s\"", ssid)
                conf.preSharedKey = String.format("\"%s\"", pass)
                val newId = wifiManager.addNetwork(conf)
                wifiManager.disconnect()
                wifiManager.enableNetwork(newId, true);
                wifiManager.reconnect()
                callback.call()
            } else {
                if (!wifiManager.isWifiEnabled) {
                    val panelIntent = Intent(Settings.Panel.ACTION_WIFI)
                    startActivityForResult(panelIntent, REQ_WIFI)
                }
                val wifiNetworkSpecifier = WifiNetworkSpecifier.Builder()
                    .setSsid(ssid)
                    .setWpa2Passphrase(pass)
                    .build()

                val networkRequest = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .setNetworkSpecifier(wifiNetworkSpecifier)
                    .build()

                val connectivityManager =
                    getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        connectivityManager.bindProcessToNetwork(network)
                        callback.call()
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        connectivityManager.bindProcessToNetwork(null)
                        connectivityManager.unregisterNetworkCallback(networkCallback)
                        callback.call()
                    }

                }
                connectivityManager.requestNetwork(networkRequest, networkCallback)


            }
        }
    }

}