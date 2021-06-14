package com.gmail.appverstas.finnishbabyname.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.finnishbabyname.MainActivity
import com.gmail.appverstas.finnishbabyname.R
import com.gmail.appverstas.finnishbabyname.data.models.Name

/**
 *Veli-Matti Tikkanen, 23.3.2021
 */
class NameListAdapter(var context: Context): RecyclerView.Adapter<NameListAdapter.ViewHolder>() {

    var nameList = emptyList<Name>()
    var nameListAll = emptyList<Name>()
    var currentGender = "boy"
    var showOnlyBlacklist = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameText = holder.itemView.findViewById<TextView>(R.id.nameTextView)
        val favouriteButton = holder.itemView.findViewById<ImageView>(R.id.btn_favourite)
        val blacklistButton = holder.itemView.findViewById<ImageView>(R.id.btn_blacklist)
        val nameCount = nameList.indexOf(nameList[position])+1
        nameText.setText(nameCount.toString() + ". " + nameList[position].name)

        if (nameList[position].isFavourite.equals("true")){
            favouriteButton.setImageResource(R.drawable.ic_heart_filled)
        }else{
            favouriteButton.setImageResource(R.drawable.ic_heart_border)
        }

        favouriteButton.setOnClickListener {
            if (nameList[position].isFavourite.equals("false")) {
                (context as MainActivity).addToFavourites(nameList[position])
            } else {
                (context as MainActivity).deleteFromFavourites(nameList[position])
            }
        }

        blacklistButton.setOnClickListener {
            if(nameList[position].isBlacklisted.equals("false")){
                (context as MainActivity).addToBlacklist(nameList[position])
            }else{
                (context as MainActivity).deleteFromBlacklist(nameList[position])
            }
        }

    }

    override fun getItemCount(): Int{
        return nameList.size
    }

    fun makeList(){
        if (showOnlyBlacklist == true){
            nameList = nameListAll.filter { name -> (name.gender.equals(currentGender)) && (name.isBlacklisted.equals("true"))}
        }else{
            nameList = nameListAll.filter { name -> name.gender.equals(currentGender) && (name.isBlacklisted.equals("false"))}
        }
        notifyDataSetChanged()
    }


    fun setOnlyBlacklist(b: Boolean){
        showOnlyBlacklist = b
    }

    fun setGender(gender: String){
        currentGender = gender
        makeList()
    }

    fun updateNameListData(updatedData: List<Name>){
        nameListAll = updatedData
        makeList()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}