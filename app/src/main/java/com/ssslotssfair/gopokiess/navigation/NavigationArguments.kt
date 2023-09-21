package com.ssslotssfair.gopokiess.navigation

import android.util.Log

class NavigationArguments(values: MutableMap<String, Any>) {
    private val privateValues = values

    fun getString(name: String): String? {
        if(!privateValues.containsKey(name)) {
            Log.w("Navigation arguments", "Not found string value by key \"${name}\". Null returned.")
        }
        return privateValues[name] as String?
    }
    fun getString(name: String, default: String): String {
        return if(privateValues.containsKey(name)) {
            privateValues[name] as String
        }
        else {
            Log.w("Navigation arguments", "Not found string value by key \"${name}\". Default returned.")
            default
        }
    }

    fun getInteger(name: String): Int? {
        if(!privateValues.containsKey(name)) {
            Log.w("Navigation arguments", "Not found integer value by key \"${name}\". Null returned.")
        }
        return privateValues[name] as Int?
    }
    fun getInteger(name: String, default: Int = 0): Int {
        return if(privateValues.containsKey(name)) {
            privateValues[name] as Int
        }
        else {
            Log.w("Navigation arguments", "Not found integer value by key \"${name}\". Default returned.")
            default
        }
    }

    fun getBoolean(name: String): Boolean? {
        if(!privateValues.containsKey(name)) {
            Log.w("Navigation arguments", "Not found boolean value by key \"${name}\". Null returned.")
        }
        return privateValues[name] as Boolean?
    }
    fun getBoolean(name: String, default: Boolean): Boolean {
        return if(privateValues.containsKey(name)) {
            privateValues[name] as Boolean
        }
        else {
            Log.w("Navigation arguments", "Not found boolean value by key \"${name}\". Default returned.")
            default
        }
    }

    fun setString(name: String, value: String) {
        privateValues[name] = value
    }

    fun setInteger(name: String, value: Int) {
        privateValues[name] = value
    }

    fun setBoolean(name: String, value: Boolean) {
        privateValues[name] = value
    }
}