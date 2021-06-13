package com.gmail.appverstas.finnishbabyname.data.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.finnishbabyname.R
import com.gmail.appverstas.finnishbabyname.data.NameListAdapter
import com.gmail.appverstas.finnishbabyname.data.models.Name
import com.gmail.appverstas.finnishbabyname.data.room.NameViewModel
import kotlinx.android.synthetic.main.fragment_name_list.*


class NameListFragment : Fragment() {

    private val nameViewModel: NameViewModel by viewModels()
    private lateinit var adapter: NameListAdapter
    private lateinit var menuItemAllNames: MenuItem
    private lateinit var menuItemFavourites: MenuItem
    private lateinit var menuItemBlacklist: MenuItem
    private lateinit var menuItemNameGenerator: MenuItem

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_list, container, false)

        val titleGender = view.findViewById<TextView>(R.id.txt_gender_title)
        val maleButton = view.findViewById<ImageView>(R.id.maleImage)
        val femaleButton = view.findViewById<ImageView>(R.id.femaleImage)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = NameListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        titleGender.text = getString(R.string.title_boy_names)
        setHasOptionsMenu(true)

        nameViewModel.getAllData.observe(viewLifecycleOwner, { updatedNameList ->
            if (updatedNameList.isEmpty()) {
                Log.d(TAG, "onCreateView: creating new database")
                println("database empty, filling it.")
                addToDatabase("boy.txt", "boy")
                addToDatabase("girl.txt", "girl")
            } else {
                Log.d(TAG, "onCreateView: database already in place")
                adapter.updateNameListData(updatedNameList)
            }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        menuItemAllNames = menu.findItem(R.id.menu_all_names)
        menuItemFavourites = menu.findItem(R.id.menu_favourites)
        menuItemBlacklist = menu.findItem(R.id.menu_blacklist)
        menuItemNameGenerator = menu.findItem(R.id.menu_name_generator)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_all_names -> showAllNames()
            R.id.menu_favourites -> showFavourites()
            R.id.menu_name_generator -> launchNameGenerator()
            R.id.menu_blacklist -> showBlacklist()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAllNames(){
        adapter.setOnlyFavourites(false)
        adapter.setOnlyBlacklist(false)
        adapter.makeList()
        menuItemAllNames.isVisible = false
        menuItemFavourites.isVisible = true
        menuItemBlacklist.isVisible = true
        menuItemNameGenerator.isVisible = true
        txt_page_title.text = getListName()
    }

    private fun showFavourites(){
        if(!adapter.showOnlyFavourites) {
            adapter.setOnlyFavourites(true)
            adapter.setOnlyBlacklist(false)
            adapter.makeList()
            menuItemAllNames.isVisible = true
            menuItemFavourites.isVisible = false
            menuItemBlacklist.isVisible = true
            menuItemNameGenerator.isVisible = true
            txt_page_title.text = getListName()
        }
    }

    private fun launchNameGenerator(){
        findNavController().navigate(R.id.action_nameListFragment_to_nameGeneratorFragment)
    }

    private fun showBlacklist(){
        if(!adapter.showOnlyBlacklist) {
            adapter.setOnlyBlacklist(true)
            adapter.setOnlyFavourites(false)
            adapter.makeList()
            menuItemAllNames.isVisible = true
            menuItemFavourites.isVisible = true
            menuItemBlacklist.isVisible = false
            menuItemNameGenerator.isVisible = true
            txt_page_title.text = getListName()
        }
    }

    private fun getListName(): String{
        if(adapter.showOnlyFavourites){
            return getString(R.string.menu_item_favourites)
        }else if(adapter.showOnlyBlacklist){
            return getString(R.string.menu_item_blacklist)
        }
        return ""
    }

    private fun addToDatabase(fileName: String, gender: String){
        val names = context?.assets?.open(fileName)?.reader()?.readLines()
        if (names != null) {
            for (name in names) {
                val newName = Name(
                        0,
                        name,
                        gender,
                        "false",
                        "false")
                nameViewModel.insertName(newName)
            }
        }
    }


}