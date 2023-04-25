package com.garsemar.flyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.databinding.FragmentLoginBinding
import com.garsemar.flyer.realm.RealmManager
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val realmManager = RealmManager()

        binding.ok.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if(!realmManager.login(binding.email.text.toString(), binding.password.text.toString())){
                    Toast.makeText(requireContext(), "Invalid email/password", Toast.LENGTH_SHORT).show()
                }else{
                    val action = LoginFragmentDirections.actionLoginFragmentToMapFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}