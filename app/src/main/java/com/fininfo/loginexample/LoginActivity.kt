package com.fininfo.loginexample

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fininfo.loginexample.ui.model.UserData
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val passwordPattern: String = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.* )(?=.*[^a-zA-Z0-9]).{7,}\$"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        changeStatusBarColor()
        preferences = this.getSharedPreferences("login", MODE_PRIVATE)
        editor = preferences.edit()
        editor.putString("userName", "Fininfocom")
        editor.putString("password", "Fin@123")
        editor.apply()
        val loginStatus: SharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        if (loginStatus.getBoolean("loginStatus", false)) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        val pattern: Pattern = Pattern.compile(passwordPattern)
        loginButton.setOnClickListener(this)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.login_bg)
    }


    fun validationCheck(): Boolean {
        if (userNameEt.text?.length!! < 10) {
            errorInSnackbar("Username Contain Must be above 10 characters")
            return false
        }
        if (passwordEt.text?.length!! < 7) {
            errorInSnackbar("Password Must contain 7 Characters")
            return false
//            if (matcher!=pattern.matcher(passwordEt.text.toString())) {
//                errorInSnackbar("Password Must have contain one Uppercase,one special Character and Numeric")
//                return false
//            }
        }
        return true
    }

    private fun errorInSnackbar(error: String) {
        val snackBar: Snackbar = Snackbar.make(parentLayout, error, Snackbar.LENGTH_LONG)
        snackBar.show()
        val sbView: View = snackBar.view
        val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        sbView.setBackgroundColor(Color.RED)
        textView.setTextColor(Color.WHITE)
        textView.setTypeface(null, Typeface.BOLD)
        snackBar.show()
    }

    override fun onClick(p0: View?) {
        val loginPreferences: SharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE)
        if (p0 == loginButton) {
            val status: Boolean = validationCheck()
            if (status) {
                if (loginPreferences.getString("userName", null).equals(userNameEt.text.toString())
                    && loginPreferences.getString("password", null).equals(passwordEt.text.toString())
                ) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    editor.putBoolean("loginStatus", true)
                    editor.apply()
                } else {
                    errorInSnackbar("Invalid Credentials")
                }
            }
        }
    }
}