package com.example.spktopsis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SplashScreen : AppCompatActivity() {
    private lateinit var button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        button = findViewById(R.id.btnStart)
        button.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        }
    }

}