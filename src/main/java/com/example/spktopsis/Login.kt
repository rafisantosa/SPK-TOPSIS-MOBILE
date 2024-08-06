package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    private lateinit var edEmail:EditText
    private lateinit var edPass:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        databaseHelper=DatabaseHelper(this)

        edEmail=findViewById(R.id.edLoginEmail)
        edPass=findViewById(R.id.edLoginPass)
        btnLogin=findViewById(R.id.btnLogin)
        btnRegister=findViewById((R.id.txtRegis))

        btnLogin.setOnClickListener {
            val email = edEmail.text.toString().trim()
            val password = edPass.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val isLoggedIn = databaseHelper.checkUser(email, password)
                if (isLoggedIn) {
                    val username = databaseHelper.getUsernameByEmail(email)
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Email atau Password Salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Jangan Kosongkan Fields", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegister.setOnClickListener{
            val intent=Intent(this,Register::class.java)
            startActivity(intent)
        }
    }
}