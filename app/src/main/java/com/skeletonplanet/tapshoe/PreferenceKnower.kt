package com.skeletonplanet.tapshoe

import androidx.preference.PreferenceManager

const val STARTING_URL = "https://skeletonplanet.com/secret/tapshoe"
const val LOCAL_URL = "file:///android_asset/www/index.html"

class PreferenceKnower(val activity: MainActivity) {
    val params = PreferenceManager.getDefaultSharedPreferences(activity);

    val storysource: String?
        get() = params.getString("story_source", null)

    val story_url: String
        get() {
            println("storysource: ${storysource}")
            return when(storysource) {
                "remote", "remote_debug" -> STARTING_URL
                "local" -> LOCAL_URL
                else -> LOCAL_URL
            }
        }

    val should_be_cached: Boolean
        get() {
            println("storysource: ${storysource}")
            return when(storysource) {
                "remote_debug" -> false
                else -> true
            }
        }
}