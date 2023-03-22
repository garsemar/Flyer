package com.garsemar.flyer

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garsemar.flyer.MainActivity.Companion.realmManager
import com.garsemar.flyer.databinding.FragmentAddBinding
import com.garsemar.flyer.databinding.FragmentHomeBinding
import com.garsemar.flyer.model.Posicions
import com.garsemar.flyer.realm.PosicionsDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("WrongThread", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realmManager.configureRealm()

        val lat = arguments?.getString("lat")
        val lan = arguments?.getString("lan")

        binding.lat.text = lat
        binding.lan.text = lan

        val bitmap = (requireContext().resources.getDrawable(R.drawable.logo,requireContext().theme) as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        val ns = realmManager.posicionsDao.listFlow()

        GlobalScope.launch{ ns.collect { println(it) } }

        binding.add.setOnClickListener{
            println(binding.title.text)
            realmManager.posicionsDao.insertItem(binding.title.text.toString(), lat!!.toDouble(), lan!!.toDouble(), image)
        }
    }
}