package com.bignerdranch.android.guestlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import org.w3c.dom.Text

const val LAST_GUEST_NAME_KEY = "Last-guest-name-bundle-key"

class MainActivity : AppCompatActivity() {

    private lateinit var addGuestButton: Button
    private lateinit var newGuestEditText: EditText
    private lateinit var guestList: TextView
    private lateinit var lastGuestAdded: TextView
    private lateinit var clearGuestListButton: Button

//    val guestNames = mutableListOf<String>()

    private val guestListViewModel: GuestListViewModel by lazy {
    // lazy initialization - lambda won't be called until the guest list viewmodel is used
        ViewModelProvider(this).get(GuestListViewModel::class.java)
    }


    //TODO Landscape Layout 19:40 in final video
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGuestButton = findViewById(R.id.add_guest_button)
        clearGuestListButton = findViewById(R.id.clear_guests_button)
        newGuestEditText = findViewById(R.id.new_guest_input)
        guestList = findViewById(R.id.list_of_guests)
        lastGuestAdded = findViewById(R.id.last_guest_added)

        addGuestButton.setOnClickListener{
            addNewGuest()
        }

        clearGuestListButton.setOnClickListener{
            clearGuestList()
        }


        val savedLastGuestMessage = savedInstanceState?.getString(LAST_GUEST_NAME_KEY)
        lastGuestAdded.text = savedLastGuestMessage

        updateGuestList() // update from view model - need when activity is destroyed and rectreated

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_GUEST_NAME_KEY, lastGuestAdded.text.toString())
    }

    private fun addNewGuest(){
        val newGuestName = newGuestEditText.text.toString()
        if(newGuestName.isNotBlank()){
//            guestNames.add(newGuestName)
            guestListViewModel.addGuest(newGuestName)
            updateGuestList()
            newGuestEditText.text.clear()
            lastGuestAdded.text = getString(R.string.last_guest_message, newGuestName)
        }

    }

    private fun updateGuestList(){
        val guests = guestListViewModel.getSortedGuestNames()
        val guestdisplay = guests.joinToString(separator = "\n")
        guestList.text = guestdisplay
    }

    private fun clearGuestList(){
        guestListViewModel.clearGuests()
        updateGuestList()
        lastGuestAdded.text = ""
    }
}