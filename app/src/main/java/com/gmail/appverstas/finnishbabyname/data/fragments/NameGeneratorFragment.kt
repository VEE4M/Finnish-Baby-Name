package com.gmail.appverstas.finnishbabyname.data.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gmail.appverstas.finnishbabyname.R
import com.gmail.appverstas.finnishbabyname.data.models.Name
import com.gmail.appverstas.finnishbabyname.data.room.NameViewModel
import kotlinx.android.synthetic.main.fragment_name_generator.*
import kotlin.random.Random


class NameGeneratorFragment : Fragment() {

    private val nameViewModel: NameViewModel by viewModels()
    private var currentGender = "boy"
    private lateinit var nameListAll: List<Name>
    private lateinit var listStartsWithVowel: MutableList<Name>
    private lateinit var listEndsWithConsonant: MutableList<Name>
    private lateinit var listStartsWithConsonant: MutableList<Name>
    private lateinit var listEndsWithVowel: MutableList<Name>

    private lateinit var currentList: MutableList<Name>

    private lateinit var firstNamesThatStartWithCorrectLetter: MutableList<Name>
    private lateinit var firstNamesThatEndWithCorrectLetter: MutableList<Name>

    private lateinit var secondNamesThatStartWithCorrectLetter: MutableList<Name>
    private lateinit var secondNamesThatEndWithCorrectLetter: MutableList<Name>

    private lateinit var thirdNamesThatStartWithCorrectLetter: MutableList<Name>
    private lateinit var thirdNamesThatEndWithCorrectLetter: MutableList<Name>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_generator, container, false)

        val maleButton = view.findViewById<ImageView>(R.id.maleImage)
        val femaleButton = view.findViewById<ImageView>(R.id.femaleImage)
        val generateNextButton = view.findViewById<Button>(R.id.btn_generate_next)

        val firstNameStartsConsonantRadio = view.findViewById<RadioButton>(R.id.firstNameStartsConsonantRadioButton)
        val firstNameStartsVowelRadio = view.findViewById<RadioButton>(R.id.firstNameStartsVowelRadioButton)
        val firstNameStartsAnyRadio = view.findViewById<RadioButton>(R.id.firstNameStartsAnyRadioButton)

        val secondNameStartsConsonantRadio = view.findViewById<RadioButton>(R.id.secondNameStartsConsonantRadioButton)
        val secondNameStartsVowelRadio = view.findViewById<RadioButton>(R.id.secondNameStartsVowelRadioButton)
        val secondNameStartsAnyRadio = view.findViewById<RadioButton>(R.id.secondNameStartsAnyRadioButton)
        val thirdNameStartsConsonantRadio = view.findViewById<RadioButton>(R.id.thirdNameStartsConsonantRadioButton)
        val thirdNameStartsVowelRadio = view.findViewById<RadioButton>(R.id.thirdNameStartsVowelRadioButton)
        val thirdNameStartsAnyRadio = view.findViewById<RadioButton>(R.id.thirdNameStartsAnyRadioButton)

        val firstNameEndsConsonantRadio = view.findViewById<RadioButton>(R.id.firstNameEndsConsonantRadioButton)
        val firstNameEndsVowelRadio = view.findViewById<RadioButton>(R.id.firstNameEndsVowelRadioButton)
        val firstNameEndsAnyRadio = view.findViewById<RadioButton>(R.id.firstNameEndsAnyRadioButton)

        val secondNameEndsConsonantRadio = view.findViewById<RadioButton>(R.id.secondNameEndsConsonantRadioButton)
        val secondNameEndsVowelRadio = view.findViewById<RadioButton>(R.id.secondNameEndsVowelRadioButton)
        val secondNameEndsAnyRadio = view.findViewById<RadioButton>(R.id.secondNameEndsAnyRadioButton)

        val thirdNameEndsConsonantRadio = view.findViewById<RadioButton>(R.id.thirdNameEndsConsonantRadioButton)
        val thirdNameEndsVowelRadio = view.findViewById<RadioButton>(R.id.thirdNameEndsVowelRadioButton)
        val thirdNameEndsAnyRadio = view.findViewById<RadioButton>(R.id.thirdNameEndsAnyRadioButton)

        val firstNameSetup = view.findViewById<ConstraintLayout>(R.id.firstNameSetupLayout)
        val firstNameShowSetupButton = view.findViewById<LinearLayout>(R.id.firstNameSetupTitleLayout)
        val secondNameSetup = view.findViewById<ConstraintLayout>(R.id.secondNameSetupLayout)
        val secondNameShowSetupButton = view.findViewById<LinearLayout>(R.id.secondNameSetupTitleLayout)
        val thirdNameSetup = view.findViewById<LinearLayout>(R.id.thirdNameSetupLayout)
        val thirdNameShowSetupButton = view.findViewById<LinearLayout>(R.id.thirdNameSetupTitleLayout)
        val enableThirdNameCheckbox = view.findViewById<CheckBox>(R.id.enableThirdNameCheckbox)

        firstNameStartsAnyRadio.isChecked = true
        firstNameEndsAnyRadio.isChecked = true
        secondNameStartsAnyRadio.isChecked = true
        secondNameEndsAnyRadio.isChecked = true
        thirdNameStartsAnyRadio.isChecked = true
        thirdNameEndsAnyRadio.isChecked = true

        firstNameSetup.visibility = View.GONE
        secondNameSetup.visibility = View.GONE
        thirdNameSetup.visibility = View.GONE


        nameViewModel.getAllData.observe(viewLifecycleOwner, Observer { updatedList ->
            nameListAll = updatedList
            listStartsWithVowel = getListStartsVowel()
            listEndsWithConsonant = getListEndsConsonant()
            listStartsWithConsonant = getListStartsConsonant()
            listEndsWithVowel = getListEndsVowel()
            firstNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            firstNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
            secondNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            secondNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
            thirdNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            thirdNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
        })

        maleButton.setOnClickListener {
            currentGender = "boy"
            maleButton.setImageResource(R.drawable.ic_gender_male_active)
            femaleButton.setImageResource(R.drawable.ic_gender_female_inactive)
        }

        femaleButton.setOnClickListener {
            currentGender = "girl"
            maleButton.setImageResource(R.drawable.ic_gender_male_inactive)
            femaleButton.setImageResource(R.drawable.ic_gender_female_active)
        }

        generateNextButton.setOnClickListener {
            firstNameSetup.visibility = View.GONE
            secondNameSetup.visibility = View.GONE
            thirdNameSetup.visibility = View.GONE
            txt_generated_name.setText("")

            var i = 0
            if(!enableThirdNameCheckbox.isChecked){
                i = 1
            }
            while(i != 3){
                var correctList = getCorrectList(i)
                var name = generateName(correctList.filter { name -> name.gender == currentGender })
                txt_generated_name.append(name + " ")
                i++
            }
        }

        firstNameShowSetupButton.setOnClickListener {
            if (firstNameSetup.isVisible){
                firstNameSetup.visibility = View.GONE
            }else{
                firstNameSetup.visibility = View.VISIBLE
            }
        }

        secondNameShowSetupButton.setOnClickListener {
            if(secondNameSetup.isVisible){
                secondNameSetup.visibility = View.GONE
            }else{
                secondNameSetup.visibility = View.VISIBLE
            }
        }

        thirdNameShowSetupButton.setOnClickListener {
            if(thirdNameSetup.isVisible){
                thirdNameSetup.visibility = View.GONE
            }else{
                thirdNameSetup.visibility = View.VISIBLE
            }
        }

        firstNameStartsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("consonant")
            }
        }

        firstNameStartsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("vowel")
            }
        }

        firstNameStartsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        firstNameEndsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("consonant")
            }
        }

        firstNameEndsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("vowel")
            }
        }

        firstNameEndsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                firstNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        /////////
        secondNameStartsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("consonant")
            }
        }

        secondNameStartsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("vowel")
            }
        }

        secondNameStartsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        secondNameEndsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("consonant")
            }
        }

        secondNameEndsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("vowel")
            }
        }

        secondNameEndsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                secondNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        /////
        thirdNameStartsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("consonant")
            }
        }

        thirdNameStartsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatStartWithCorrectLetter = getListWithCorrectStartLetter("vowel")
            }
        }

        thirdNameStartsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatStartWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        thirdNameEndsConsonantRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("consonant")
            }
        }

        thirdNameEndsVowelRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatEndWithCorrectLetter = getListWithCorrectEndLetter("vowel")
            }
        }

        thirdNameEndsAnyRadio.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                thirdNamesThatEndWithCorrectLetter = nameListAll.toMutableList()
            }
        }

        return view
    }

    private fun getCorrectList(name: Int): MutableList<Name> {
        currentList = mutableListOf()
        if (name == 0) {
            for (Name in firstNamesThatStartWithCorrectLetter) {
                if (firstNamesThatEndWithCorrectLetter.contains(Name)) {
                    currentList.add(Name)
                }
            }
        } else if (name == 1) {
            for (Name in secondNamesThatStartWithCorrectLetter) {
                if (secondNamesThatEndWithCorrectLetter.contains(Name)) {
                    currentList.add(Name)
                }
            }
        } else {
            for (Name in thirdNamesThatStartWithCorrectLetter) {
                if (thirdNamesThatEndWithCorrectLetter.contains(Name)) {
                    currentList.add(Name)
                }
            }
        }
        return currentList
    }

    private fun getListWithCorrectStartLetter(mode: String): MutableList<Name>{
        if(mode.equals("vowel")){
            return listStartsWithVowel
        }else{
            return listStartsWithConsonant
        }
    }

    private fun getListWithCorrectEndLetter(mode: String): MutableList<Name>{
        if(mode.equals("vowel")){
            return listEndsWithVowel
        }else{
            return listEndsWithConsonant
        }
    }

    private fun generateName(list: List<Name>): String{
        val maxCount = list.size-1
        var randomNumber = Random.nextInt(maxCount)
        return list[randomNumber].name
    }


    private fun getListStartsVowel(): MutableList<Name>{
        var list = mutableListOf<Name>()
        for (Name in nameListAll){
            if(startsWithVowel(Name.name)){
                list.add(Name)
            }
        }
        return list
    }

    private fun getListEndsConsonant(): MutableList<Name>{
        var list = mutableListOf<Name>()
        for (Name in nameListAll){
            if(endsWithConsonant(Name.name)){
                list.add(Name)
            }
        }
        return list
    }

    private fun getListStartsConsonant(): MutableList<Name>{
        var list = mutableListOf<Name>()
        for (Name in nameListAll){
            if(startsWithConsonant(Name.name)){
                list.add(Name)
            }
        }
        return list
    }

    private fun getListEndsVowel(): MutableList<Name>{
        var list = mutableListOf<Name>()
        for (Name in nameListAll){
            if(endsWithVowel(Name.name)){
                list.add(Name)
            }
        }
        return list
    }

    private fun startsWithVowel(name: String): Boolean {
        if ((name.startsWith("A", 0,true)) ||
            (name.startsWith("E", 0,true)) ||
            (name.startsWith("I", 0,true)) ||
            (name.startsWith("O", 0,true)) ||
            (name.startsWith("U", 0,true)) ||
            (name.startsWith("Y", 0,true)) ||
            (name.startsWith("Ö", 0,true)) ||
            (name.startsWith("Ä", 0,true)) ||
            (name.startsWith("Å", 0,true)) ||
            (name.startsWith("é", 0,true))
        ) {
            return true
        }
        return false
    }

   private fun endsWithVowel(name: String): Boolean{
       if ((name.endsWith("A", true)) ||
           (name.endsWith("E", true)) ||
           (name.endsWith("I", true)) ||
           (name.endsWith("O", true)) ||
           (name.endsWith("U", true)) ||
           (name.endsWith("Y", true)) ||
           (name.endsWith("Ö", true)) ||
           (name.endsWith("Ä", true)) ||
           (name.endsWith("Å", true)) ||
           (name.endsWith("é", true))
       ) {
           return true
       }
       return false
   }

    private fun startsWithConsonant(name: String): Boolean{
        if ((name.startsWith("B", 0, true))||
            (name.startsWith("C", 0, true))||
            (name.startsWith("D", 0,true)) ||
            (name.startsWith("F", 0,true)) ||
            (name.startsWith("G", 0,true)) ||
            (name.startsWith("H", 0,true)) ||
            (name.startsWith("J", 0,true)) ||
            (name.startsWith("K", 0,true)) ||
            (name.startsWith("L", 0,true)) ||
            (name.startsWith("M", 0,true)) ||
            (name.startsWith("N", 0,true)) ||
            (name.startsWith("P", 0,true)) ||
            (name.startsWith("Q", 0,true)) ||
            (name.startsWith("R", 0,true)) ||
            (name.startsWith("S", 0,true)) ||
            (name.startsWith("T", 0,true)) ||
            (name.startsWith("V", 0,true)) ||
            (name.startsWith("W", 0,true)) ||
            (name.startsWith("X", 0,true)) ||
            (name.startsWith("Z", 0,true))
        ) {
            return true
        }
        return false
    }

    private fun endsWithConsonant(name: String): Boolean{
        if ((name.endsWith("B", true))||
            (name.endsWith("C",true))||
            (name.endsWith("D", true)) ||
            (name.endsWith("F", true)) ||
            (name.endsWith("G", true)) ||
            (name.endsWith("H", true)) ||
            (name.endsWith("J", true)) ||
            (name.endsWith("K", true)) ||
            (name.endsWith("L", true)) ||
            (name.endsWith("M", true)) ||
            (name.endsWith("N", true)) ||
            (name.endsWith("P", true)) ||
            (name.endsWith("Q", true)) ||
            (name.endsWith("R", true)) ||
            (name.endsWith("S", true)) ||
            (name.endsWith("T", true)) ||
            (name.endsWith("V", true)) ||
            (name.endsWith("W", true)) ||
            (name.endsWith("X", true)) ||
            (name.endsWith("Z", true))
        ) {
            return true
        }
        return false
    }


}