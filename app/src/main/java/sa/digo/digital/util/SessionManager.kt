package sa.digo.digital.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import sa.digo.digital.R

class SessionManager {
    private var sp: SharedPreferences? = null
    private var spEditor: SharedPreferences.Editor? = null
    constructor(context: Context?){
        sp = PreferenceManager.getDefaultSharedPreferences(context)
    }
    fun clear() {
        spEditor = sp!!.edit().clear()
        with (sp!!.edit()) {
            clear()
            apply()
        }
    }

    fun setBoolean(key: String?, value: Boolean): Boolean {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putBoolean(key, value)
            apply()
        }

        return true
    }

    fun getBoolean(key: String?): Boolean {
        return sp!!.getBoolean(key, false)
    }

    fun setFloat(key: String?, value: Float): Boolean {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putFloat(key, value)
            apply()
        }
        return true
    }

    fun getFloat(key: String?): Float {
        return sp!!.getFloat(key, -1f)
    }

    fun setLong(key: String?, value: Long): Boolean {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putLong(key, value)
            apply()
        }
        return true
    }

    fun getLong(key: String?): Long {
        return sp!!.getLong(key, -1)
    }

    fun setInt(key: String?, value: Int): Boolean {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putInt(key, value)
            apply()
        }
        return true
    }

    fun getInt(key: String?): Int {
        return sp!!.getInt(key, -1)
    }

    //                                sessionManager.setString("user", json);
    fun setString(key: String?, value: String?): Boolean {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putString(key, value)
            apply()
        }
        return true
    }

    fun getString(key: String?): String? {
        return try {
            sp!!.getString(key, null)
        } catch (e: Exception) {
            null
        }
    }

    fun setObject(key: String?, value: Any?): Boolean {
        spEditor = sp!!.edit()
        val gson = Gson()
        val myObject = gson.toJson(value)
        with (sp!!.edit()) {
            putString(key, myObject)
            apply()
        }
        return true
    }

    fun <T> getObject(key: String?, classType: Class<T>?): T? {
        return try {
            val gson = Gson()
            val json = sp!!.getString(key, "")
            gson.fromJson(json, classType)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> setArray(key: String?, list: List<T>?) {
        spEditor = sp!!.edit()
        with (sp!!.edit()) {
            putString(key, Gson().toJson(list))
            apply()
        }

    }

    fun <T> getArray(key: String?, classType: Class<T>?): List<T>? {
        spEditor = sp!!.edit()
        val typeOfT = TypeToken.getParameterized(MutableList::class.java, classType).type
        return Gson().fromJson(sp!!.getString(key, ""), typeOfT)
    }

}