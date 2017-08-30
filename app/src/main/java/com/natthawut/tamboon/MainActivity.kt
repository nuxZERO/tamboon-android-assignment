package com.natthawut.tamboon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.natthawut.tamboon.ui.charities.CharitiesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initFragment(CharitiesFragment())
        }

    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.content_layout, fragment)
                .commit()
    }

}
