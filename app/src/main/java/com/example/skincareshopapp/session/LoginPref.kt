package com.example.skincareshopapp.session

import android.accounts.AccountManager.KEY_PASSWORD
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import com.example.skincareshopapp.activity.CartActivity
import com.example.skincareshopapp.activity.LoginActivity
import com.example.skincareshopapp.activity.MainActivity
import com.example.skincareshopapp.activity.ProductInfoActivity

class LoginPref(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    companion object{
        val PREF_NAME = "Login_Preference"
        val IS_LOGIN = "isLogged"
        val USER_EMAIL = "email"
        val USER_PASSWORD = "password"
        val USER_ID = "userId"
    }
    fun setSignIn(userId:Int){
        editor.putBoolean(IS_LOGIN,true)
        editor.putInt(USER_ID,userId)
        editor.apply()
    }

    fun getCurrentUser():String?{
        return sharedPreferences.getString(USER_ID, "")
    }

    fun createLoginSession(email:String,password:String){
        editor.putString(USER_EMAIL,email)
        editor.putString(USER_PASSWORD,password)
        editor.apply()
    }

    fun getUserDetail():HashMap<String,String>{
        var user:Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(USER_EMAIL,sharedPreferences.getString(USER_EMAIL,null)!!)
        (user as HashMap).put(USER_PASSWORD,sharedPreferences.getString(USER_PASSWORD,null)!!)
        return user
    }

    fun logOut(){
        editor.clear()
        editor.commit()
    }
    fun isLogged():Boolean{
         return sharedPreferences.getBoolean(IS_LOGIN,false)
    }
}