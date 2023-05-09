package com.garsemar.flyer

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.garsemar.flyer.MainActivity.Companion.realmManager
import com.garsemar.flyer.MainActivity.Companion.v_supportActionBar
import com.garsemar.flyer.databinding.FragmentDetailBinding
import io.realm.kotlin.types.ObjectId

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        v_supportActionBar.title = "Flyer"
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stringId = arguments?.getString("id")

        val id = ObjectId.from(stringId!!)

        val list = realmManager.posicionsDao.listFlow().filter { it._id == id }

        binding.delete.setOnClickListener {
            realmManager.posicionsDao.deleteItem(id)
            Toast.makeText(requireContext(), "Deleted!", Toast.LENGTH_SHORT).show()
            val action = DetailFragmentDirections.actionDetailFragmentToBookmarksFragment()
            findNavController().navigate(action)
        }

        binding.title.text = list.first().title
        binding.coords.text = """
            Lat: ${list.first().lat}
            Lon: ${list.first().lon}
        """.trimIndent()
        val imageByteArray = list.first().image
        val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray!!.size)
        binding.image.setImageBitmap(bitmap)
    }
}