package com.skeletonplanet.tapshoe

import android.content.res.Resources
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SettingsActivitySystem(name: String, activity: MainActivity, permissionRequestCode: Int):
    JavascriptAccessibleSystem(name, activity, permissionRequestCode) {
    var have_position_permission = false
    var requested_position_permission = false
    var location: Location? = null
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun cmd(what: String, data: String): String {
        return when(what) {
            "start" -> start_settings_activity()
            "get" -> getPreferences()
            else -> "error: unknown what parameter ${what}"
        }
    }

    fun start_settings_activity(): String {
        val intent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(intent)
        return "done: started"
    }

    fun getPreferences(): String {
        return "done: whatever"
    }
}
