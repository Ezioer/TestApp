package com.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.basesimpledialog.SimpleDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SimpleDialog.Builder(this).setTitle("title").build()
    }
}
