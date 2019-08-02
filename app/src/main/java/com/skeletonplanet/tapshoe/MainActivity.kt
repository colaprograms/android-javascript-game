package com.skeletonplanet.tapshoe

import android.Manifest
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

fun complain(s: String) {
    println("Tapshoe: ${s}")
}

class InterceptWebViewClient: WebViewClient() {
    @TargetApi(24)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return false; // load in the webview
    }

    // for API < 24
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false; // load in the webview
    }
    /*
    @TargetApi(24)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if(request != null && request.url.scheme == "https" && request.url.host == "skeletonplanet.com") {
            return false // load in the webview
        }
        else {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        // for API < 24
        if(url.startsWith("https://skeletonplanet.com/")) {
            return false // load in the webview
        }
        else {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }
    */
}

class MainActivity : AppCompatActivity() {
    private lateinit var webview: WebView
    private lateinit var js_interface: JS_Interface
    var jssystemindex = mutableMapOf<String, JavascriptAccessibleSystem>()
    private var permissionCodeIndex = mutableMapOf<Int, String>()
    private lateinit var preferenceknower: PreferenceKnower

    fun add_permission_callback(permissionRequestCode: Int, system: String) {
        permissionCodeIndex[permissionRequestCode] = system
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceknower = PreferenceKnower(this)
        js_interface = JS_Interface(this)
        webview = makewebview()
        LocationSystem("location", this, 0).addtoindices()
        SettingsActivitySystem("settingsactivity", this, 1).addtoindices()
    }

    fun loadUrl(url: String) {
        webview.loadUrl(url)
    }

    fun run_javascript(js: String) {
        complain("running javascript: ${js}")
        loadUrl("javascript:${js}")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val system = jssystemindex[ permissionCodeIndex[requestCode] ]
        system!!.permissionsCallback(requestCode, permissions, grantResults)
    }

    fun makewebview(): WebView {
        val webview = findViewById<WebView>(R.id.webview)
        webview.settings.apply {
            javaScriptEnabled = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            if(!preferenceknower.should_be_cached) {
                setAppCacheEnabled(false)
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
        }
        webview.addJavascriptInterface(js_interface, "android")
        webview.webViewClient = InterceptWebViewClient()
        webview.loadUrl(STARTING_URL)
        return webview
    }

    override fun onPause() {
        super.onPause()
        jssystemindex.values.forEach { it.pause() }
    }

    override fun onResume() {
        super.onResume()
        jssystemindex.values.forEach { it.resume() }
    }
}
