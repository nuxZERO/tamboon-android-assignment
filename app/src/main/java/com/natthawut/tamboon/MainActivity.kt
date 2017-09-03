package com.natthawut.tamboon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.natthawut.tamboon.ui.charities.CharitiesFragment
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fabric.with(this, Crashlytics())

        if (savedInstanceState == null) {
            initFragment(CharitiesFragment())
        }

    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_layout, fragment)
                .commit()
    }

    fun addBackStackFragment(fragment: Fragment, fragmentName: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_layout, fragment)
                .addToBackStack(fragmentName)
                .commit()
    }

}
