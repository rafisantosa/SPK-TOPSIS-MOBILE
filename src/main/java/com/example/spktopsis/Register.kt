package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {

    private lateinit var edNama:EditText
    private lateinit var edEmail:EditText
    private lateinit var edPass:EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: TextView

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        databaseHelper=DatabaseHelper(this)

        edNama=findViewById(R.id.edNamaRegis)
        edEmail=findViewById(R.id.edEmailRegis)
        edPass=findViewById(R.id.edPassRegis)
        btnRegister=findViewById(R.id.btnRegis)
        btnLogin=findViewById(R.id.txtLogin)

        btnRegister.setOnClickListener {
            val nama=edNama.text.toString().trim()
            val email=edEmail.text.toString().trim()
            val pass=edPass.text.toString().trim()

            if (nama.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()) {
                val id = databaseHelper.registerUser(nama, email, pass)
                if (id > 0) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }

        }

        btnLogin.setOnClickListener{
            val intent = Intent (this,Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}