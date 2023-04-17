package com.garsemar.flyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.garsemar.flyer.MainActivity.Companion.realmManager
import com.garsemar.flyer.MainActivity.Companion.v_supportActionBar
import com.garsemar.flyer.databinding.FragmentBookmarksBinding
import com.garsemar.flyer.databinding.ItemUserBinding
import com.garsemar.flyer.model.Posicions
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.*

class BookmarksFragment : Fragment(), OnClickListener {
    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: FragmentBookmarksBinding

    var myData = realmManager.posicionsDao.listFlow()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        v_supportActionBar.title = "Flyer"
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onClickListener = this

        userAdapter = UserAdapter(myData, onClickListener)
        linearLayoutManager = LinearLayoutManager(context)


        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
            GlobalScope.launch {
                while (myData.isEmpty()) {
                    delay(1000)
                }
                activity?.runOnUiThread {
                    adapter = UserAdapter(myData, onClickListener)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getUsers(): List<Posicions> {
        myData = realmManager.posicionsDao.listFlow()
        println(myData)
        return myData
    }

    override fun onClick(bookId: Posicions) {
        val action = BookmarksFragmentDirections.actionBookmarksFragmentToDetailFragment()
        action.id = bookId._id.toString()
        findNavController().navigate(action)


        /*parentFragmentManager.setFragmentResult(
            "Result", bundleOf("gameId" to gameId)
        )
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, DetailFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
            commit()
        }*/
    }
}

class UserAdapter(private val results: List<Posicions>, private val listener: OnClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var context: Context
    var gameId = 1

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemUserBinding.bind(view)
        fun setListener(gameId: Posicions){
            binding.root.setOnClickListener {
                listener.onClick(gameId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        with(holder){
            setListener(result)
            binding.title.text = result.title
            binding.coords.text = "Lat: ${result.lat}, Lon: ${result.lon}"
        }
    }
}