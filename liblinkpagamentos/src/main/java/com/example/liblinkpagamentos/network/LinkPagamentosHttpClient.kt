package com.example.liblinkpagamentos.network

import android.content.Context
import com.example.liblinkpagamentos.extension.addBearerFormat
import com.example.liblinkpagamentos.extension.toStatusCode
import com.example.liblinkpagamentos.models.ClientResultModel
import com.example.liblinkpagamentos.models.HttpStatusCode
import com.example.liblinkpagamentos.models.linkpagamentos.LinkPagamentosApi
import com.example.liblinkpagamentos.models.linkpagamentos.Transaction
import com.example.liblinkpagamentos.models.linkpagamentos.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LinkPagamentosHttpClient {


    fun getLink(context: Context, model: Transaction, token: String, callback: (String) -> Unit) {

        val authorizationFormat = token!!.addBearerFormat()

        val webClient =
            WebClient("https://meucheckoutsandbox.braspag.com.br/api/public/")
        val call = webClient.createService(LinkPagamentosApi::class.java)
            .postTransaction(authorizationFormat, model)

        call.enqueue(object : Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                ClientResultModel(
                    null,
                    HttpStatusCode.Unknown
                )
            }

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                ClientResultModel(
                    result = response.body(),
                    statusCode = response.code().toStatusCode()
                )
                val getUrl = response.body()!!.shortUrl.toString()
                callback?.invoke(getUrl)
            }
        })
    }
}