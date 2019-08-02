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
            "update" -> ask_for_update()
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

    fun ask_for_update(): String {
        if(have_position_permission) {
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        location = it
                    }
                return "done: updating"
            } catch(e: SecurityException) {
                return "error: we should have permission to read the location, but we don't"
            }
        }
        else if(requested_position_permission) {
            return "error: don't have permission yet or were refused"
        }
        else {
            return "error: system not started"
        }
    }

    override fun gotpermission(what: String) {
        if (what == Manifest.permission.ACCESS_FINE_LOCATION) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            have_position_permission = true
            ask_for_update()
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