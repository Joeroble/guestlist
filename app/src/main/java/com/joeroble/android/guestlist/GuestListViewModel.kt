package com.joeroble.android.guestlist

import androidx.lifecycle.ViewModel

class GuestListViewModel: ViewModel() {

    private val guestNames = mutableListOf<String>()

    fun addGuest(name: String){
        guestNames.add(name)
    }

    fun getSortedGuestNames(): List<String>{
        return guestNames.sorted()
    }

    fun clearGuests(){
        guestNames.clear()
    }
}