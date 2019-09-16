package com.example.liblinkpagamentos

interface CieloPaymentsLinkCallback {
    fun onGetLink(link: String)
    fun onError(error: String)
}