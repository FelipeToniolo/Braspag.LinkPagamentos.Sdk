package com.braspag.liblinkpagamentos.network

import com.braspag.liblinkpagamentos.BuildConfig
import com.braspag.liblinkpagamentos.service.Environment
import com.braspag.liblinkpagamentos.extension.addBearerFormat
import com.braspag.liblinkpagamentos.models.paymentlink.LinkPagamentosApi
import com.braspag.liblinkpagamentos.models.paymentlink.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LinkPagamentosHttpClient(private val environment: Environment) {

    fun getLink(
        model: Transaction, token: String,
        onGetLinkCallback: (String) -> Unit,
        onErrorCallback: (String) -> Unit
    ) {

        val authorizationFormat = token.addBearerFormat()

        val webClient =
            WebClient(useSandbox(environment))
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

    private fun useSandbox(environment: Environment): String {
        return if (environment == Environment.SANDBOX)
            BuildConfig.URL_LINKPAGAMENTOS_SANDBOX
        else
            BuildConfig.URL_LINKPAGAMENTOS_PRODUCTION
    }
}