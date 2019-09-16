package com.example.liblinkpagamentos

interface CieloPaymentsLinkCallbacks {
    fun onGetLink(link: String)
    fun onError(error: String)
}