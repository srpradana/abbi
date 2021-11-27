package com.informatika.abbi.utils

import android.content.Context

internal class StatePreference(context: Context) {
    companion object{
        const val PREFS_NAME = "state_pref"
        const val STATE_SWITCH = "state_switch"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStateSwitch(value: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(STATE_SWITCH, value)
        editor.apply()
    }

    fun getStateSwitch(): Boolean {
        return preferences.getBoolean(STATE_SWITCH, false)
    }
}