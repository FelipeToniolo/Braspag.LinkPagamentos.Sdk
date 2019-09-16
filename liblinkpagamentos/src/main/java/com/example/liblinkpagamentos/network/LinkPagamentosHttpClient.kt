package com.example.liblinkpagamentos.network

import com.example.liblinkpagamentos.extension.addBearerFormat
import com.example.liblinkpagamentos.models.HttpStatusCode
import com.example.liblinkpagamentos.models.linkpagamentos.LinkPagamentosApi
import com.example.liblinkpagamentos.models.linkpagamentos.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LinkPagamentosHttpClient {


    fun getLink(
        model: Transaction, token: String,
        onGetLinkCallback: (String) -> Unit,
        onErrorCallback: (String) -> Unit
    ) {

        val authorizationFormat = token!!.addBearerFormat()

        val webClient =
            WebClient("https://meucheckoutsandbox.braspag.com.br/api/public/")
        val call = webClient.createService(LinkPagamentosApi::class.java)
            .postTransaction(authorizationFormat, model)

        call.enqueue(object : Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                onErrorCallback.invoke(t.message.toString())
            }

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                val shortUrl = response.body()?.shortUrl
                shortUrl?.let {
                    onGetLinkCallback.invoke(it)
                }
                if (shortUrl.isNullOrBlank()) {
                    onErrorCallback.invoke("Invalid link")
                }
            }
        })
    }
}