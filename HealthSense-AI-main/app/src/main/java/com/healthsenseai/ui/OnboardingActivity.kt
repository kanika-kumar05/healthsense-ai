package com.healthsenseai.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.healthsenseai.adapters.OnboardingAdapter
import com.healthsenseai.databinding.ActivityOnboardingBinding
import com.healthsenseai.models.OnboardingPage
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseReady = FirebaseApp.initializeApp(this) != null
        if (firebaseReady && FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        val pages = listOf(
            OnboardingPage(
                title = getString(com.healthsenseai.R.string.onboard_title_1),
                subtitle = getString(com.healthsenseai.R.string.onboard_sub_1)
            ),
            OnboardingPage(
                title = getString(com.healthsenseai.R.string.onboard_title_2),
                subtitle = getString(com.healthsenseai.R.string.onboard_sub_2)
            ),
            OnboardingPage(
                title = getString(com.healthsenseai.R.string.onboard_title_3),
                subtitle = getString(com.healthsenseai.R.string.onboard_sub_3)
            )
        )

        val adapter = OnboardingAdapter(pages)
        binding.onboardingRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.onboardingRecycler.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(binding.onboardingRecycler)

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}