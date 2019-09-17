package com.braspag.liblinkpagamentos.models.paymentlink

interface CieloPaymentsLinkCallbacks {
    fun onGetLink(link: String)
    fun onError(error: String)
}