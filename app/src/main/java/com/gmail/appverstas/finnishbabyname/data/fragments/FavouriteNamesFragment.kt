package com.gmail.appverstas.finnishbabyname.data.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.finnishbabyname.R
import com.gmail.appverstas.finnishbabyname.data.NameListAdapter
import com.gmail.appverstas.finnishbabyname.data.room.NameViewModel

class FavouriteNamesFragment : Fragment() {

    private val nameViewModel: NameViewModel by viewModels()
    private lateinit var adapter: NameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourite_names, container, false)
        val femaleButton = view.findViewById<ImageView>(R.id.favouritesFemaleImage)
        val maleButton = view.findViewById<ImageView>(R.id.favouritesMaleImage)
        val titleGender = view.findViewById<TextView>(R.id.txt_favourites_gender_title)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favouritesRecyclerView)
        adapter = NameListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        nameViewModel.getAllData.observe(viewLifecycleOwner, { updatedNameList ->
            val listOfFavourites = updatedNameList.filter { name -> name.isFavourite == "true"}
            adapter.updateNameListData(listOfFavourites)
        })

        femaleButton.setOnClickListener {
            if(adapter.currentGender == "boy") {
                adapter.setGender("girl")
                titleGender.text = getString(R.string.title_girl_names)
                femaleButton.setImageResource(R.drawable.ic_gender_female_active)
                maleButton.setImageResource(R.drawable.ic_gender_male_inactive)
            }
        }

        maleButton.setOnClickListener {
            if(adapter.currentGender == "girl"){
                adapter.setGender("boy")
                titleGender.text = getString(R.string.title_boy_names)
                maleButton.setImageResource(R.drawable.ic_gender_male_active)
                femaleButton.setImageResource(R.drawable.ic_gender_female_inactive)
            }
        }
        return view
    }

}