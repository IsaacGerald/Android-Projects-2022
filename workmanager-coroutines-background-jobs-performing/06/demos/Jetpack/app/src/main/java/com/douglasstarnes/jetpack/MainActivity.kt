package com.douglasstarnes.jetpack

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var txtStatus:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtStatus = findViewById(R.id.txtStatus)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnLogin.setOnClickListener {
            viewModel.login(txtUsername.text.toString(), txtPassword.text.toString())
        }

        viewModel.LoginStatus.observe(this, Observer {
            if (it) {
                txtStatus.setTextColor(Color.rgb(0, 128, 0))
                txtStatus.text = "Logged in"
            } else {
                txtStatus.setTextColor(Color.RED)
                txtStatus.text = "Not logged in"
            }
        })
    }
}