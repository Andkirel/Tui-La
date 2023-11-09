package com.example.tui_la

interface ICommunicatorHome {
    fun passDataToFragment(uid: String)
    // add anything needed here in the arguments
    // for the interface to pass around for the current user.
    // After doing so, make sure to comb through where this is called and make
    // sure you are setting a value to that argument if it needs to be non-null.
    // ie. Journal data, name, etc..
    // ie. ...(uid: String, journalID: String, date: LocalDateTime)...
}
