package com.ssssdfd.supervisorhelperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /* if (savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.maContainer, LogInFragment())
                .commit()
        }*/
    }
}