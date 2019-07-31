package com.skeletonplanet.tapshoe

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

class LocationSystem(name: String, activity: MainActivity, permissionRequestCode: Int):
        JavascriptAccessibleSystem(name, activity, permissionRequestCode) {
    var have_position_permission = false
    var requested_position_permission = false
    var location: Location? = null
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun cmd(what: String, data: String): String {
        return when(what) {
            "start" -> start_system()
            "get" -> getLocation()
            else -> "error: unknown what parameter ${what}"
        }
    }

    fun start_system(): String {
        if(!requested_position_permission) {
            requested_position_permission = true
            request_permission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        return "done: started"
    }

    override fun gotpermission(what: String) {
        if (what == Manifest.permission.ACCESS_FINE_LOCATION) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            have_position_permission = true
            try {
                complain("trying last location")
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        complain("got location")
                        location = it
                    }
            } catch (e: SecurityException) {
                complain("we should have permission, but somehow we don't")
                throw e
            }
        }
    }

    fun getLocation(): String {
        return "done: " + locationAsJavascriptString()
    }

    fun locationAsJavascriptString(): String {
        return when(val loc = location) {
            null -> "null"
            else -> "{latitude: ${loc.latitude}, longitude: ${loc.longitude}}"
        }
    }
}

/*
    fun stopLocationUpdates() {
        //fusedLocationClient?.removeLocationUpdates
    }

    fun startLocationUpdates() {
        //fusedLocationClient?.requestLocationUpdates
    }
*/