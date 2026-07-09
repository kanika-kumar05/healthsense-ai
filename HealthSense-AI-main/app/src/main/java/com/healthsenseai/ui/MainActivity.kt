package com.healthsenseai.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthsenseai.databinding.ActivityMainBinding
import com.healthsenseai.ui.fragments.HomeFragment
import com.healthsenseai.ui.fragments.InsightsFragment
import com.healthsenseai.ui.fragments.PredictionsFragment
import com.healthsenseai.ui.fragments.ProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.healthsenseai.R
import com.healthsenseai.ui.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "HealthSenseAI"

        loadFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.healthsenseai.R.id.nav_home -> loadFragment(HomeFragment())
                com.healthsenseai.R.id.nav_insights -> loadFragment(InsightsFragment())
                com.healthsenseai.R.id.nav_predictions -> loadFragment(PredictionsFragment())
                com.healthsenseai.R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.healthsenseai.R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.healthsenseai.R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val client = GoogleSignIn.getClient(this, gso)
                client.signOut().addOnCompleteListener {
                    Toast.makeText(this, getString(com.healthsenseai.R.string.logout_success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(com.healthsenseai.R.id.container, fragment)
            .commit()
    }
}
