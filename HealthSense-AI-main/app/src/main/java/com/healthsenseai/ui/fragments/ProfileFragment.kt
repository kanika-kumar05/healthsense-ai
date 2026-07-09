package com.healthsenseai.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.healthsenseai.databinding.FragmentProfileBinding
import com.healthsenseai.databinding.DialogEditProfileBinding
import com.healthsenseai.viewmodel.ProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.healthsenseai.ui.LoginActivity
import com.healthsenseai.ui.SelfAssessmentActivity
import android.widget.Toast
import com.healthsenseai.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Observe LiveData and update UI
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.userName.text = name
        }
        
        viewModel.smartwatchStatus.observe(viewLifecycleOwner) { status ->
            binding.smartwatchStatus.text = status
        }
        
        viewModel.healthScore.observe(viewLifecycleOwner) { score ->
            binding.healthScore.text = score
        }
        
        viewModel.accountCreated.observe(viewLifecycleOwner) { created ->
            binding.accountCreated.text = created
        }

        // Self-Assessment Button
        binding.btnSelfAssessment.setOnClickListener {
            startActivity(Intent(requireContext(), SelfAssessmentActivity::class.java))
        }

        // Edit Profile Button
        binding.btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        // Logout Button
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val client = GoogleSignIn.getClient(requireContext(), gso)
            client.signOut().addOnCompleteListener {
                Toast.makeText(requireContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(layoutInflater)
        
        // Pre-fill with current values
        dialogBinding.editUserName.setText(viewModel.userName.value)
        dialogBinding.editSmartwatchStatus.setText(viewModel.smartwatchStatus.value)
        
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
        
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        
        dialogBinding.btnSave.setOnClickListener {
            val newName = dialogBinding.editUserName.text.toString().trim()
            val newStatus = dialogBinding.editSmartwatchStatus.text.toString().trim()
            
            if (newName.isNotEmpty()) {
                viewModel.updateProfile(newName, newStatus)
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        
        dialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
