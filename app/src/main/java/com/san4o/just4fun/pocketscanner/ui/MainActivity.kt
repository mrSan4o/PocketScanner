package com.san4o.just4fun.pocketscanner.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.san4o.just4fun.pocketscanner.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requestWindowFeature(Window.FEATURE_NO_TITLE)
////        window.setFlags(
////            WindowManager.LayoutParams.FLAG_FULLSCREEN,
////            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_menu_item -> {
                val fragment = VersionFragment()
                fragment.show(supportFragmentManager, fragment.javaClass.simpleName)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }
}
