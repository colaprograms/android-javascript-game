package com.skeletonplanet.tapshoe

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

typealias ParameterMap = Map<String, String>

fun causeJavascriptException(msg: String): ParameterMap {
    return mapOf(
        "result" to "error",
        "msg" to msg
    )
}

fun validResult(value: String): ParameterMap {
    return mapOf(
        "result" to "done",
        "value" to value
    )
}

open class JavascriptAccessibleSystem(val name: String, val activity: MainActivity, val permissionRequestCode: Int) {
    open fun addtoindices() {
        activity.jssystemindex[name] = this
        activity.add_permission_callback(permissionRequestCode, name)
    }

    open fun gotpermission(what: String) {
        complain("we got permission ${what}")
    }

    open fun cmd(what: String, data: String): String {
        return "error: cmd not implemented for this system"
    }

    open fun pause() {}

    open fun resume() {}

    fun request_permission(what: String) {
        val havePermission = { ContextCompat.checkSelfPermission(activity, what) == PackageManager.PERMISSION_GRANTED }
        val requestPermissions = { ActivityCompat.requestPermissions(activity, arrayOf(what), permissionRequestCode) }
        if(!havePermission()) {
            requestPermissions()
        }
        else {
            complain("we already have permission ${what}")
            gotpermission(what)
        }
    }

    open fun permissionsCallback(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == permissionRequestCode) {
            for (i in 0 until permissions.size) {
                complain("${permissions[i]}, ${grantResults[i]}")
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    gotpermission(permissions[i])
                }
            }
        }
    }
}