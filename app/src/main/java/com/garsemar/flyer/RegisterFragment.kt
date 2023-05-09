package com.garsemar.flyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.databinding.FragmentLoginBinding
import com.garsemar.flyer.databinding.FragmentRegisterBinding
import com.garsemar.flyer.realm.RealmManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val realmManager = RealmManager()

        binding.ok.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                if(binding.password.text.toString() == binding.passwordConfirm.text.toString()){
                    realmManager.register(binding.email.text.toString(), binding.password.text.toString())
                    val action = RegisterFragmentDirections.actionRegisterFragmentToMapFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}