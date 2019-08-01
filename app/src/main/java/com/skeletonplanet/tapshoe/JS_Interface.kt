package com.skeletonplanet.tapshoe

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface


/* activity has to be private or some versions of the api will be able to see it */
class JS_Interface(private val activity: MainActivity) {
    @JavascriptInterface
    fun cmd(system: String, what: String, data: String): String {
        return when(val s = activity.jssystemindex.get(system)) {
            null -> "error: no such system ${s}"
            else ->
                try {
                    s.cmd(what, data)
                }
                catch(e: Exception) {
                    "error: exception: " + e.localizedMessage + "\n" + e.stackTrace
                }
        }
    }

    @JavascriptInterface
    fun showexception(str: String) {
        val intent = Intent(activity, exceptionactivity::class.java).apply {
            putExtra(EXCEPTION_TEXT, str)
        }
        activity.startActivity(intent)
    }
    /*@JavascriptInterface
    fun test() : Int {
        return 4
    }
    @JavascriptInterface
    fun filesdirectory(): String {
        return activity.filesDir.absolutePath // /data/user/0/com.skeletonplanet.tapshoe/files
    }

    */

}