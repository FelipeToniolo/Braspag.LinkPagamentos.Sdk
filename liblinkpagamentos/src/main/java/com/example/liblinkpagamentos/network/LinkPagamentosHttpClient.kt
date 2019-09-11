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

    fun getLink(context: Context, model: Transaction) {

        val getUrl = TransactionResponse().url
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGllbnRfbmFtZSI6Ik15IENvbXBhbnkgTHRkYS4iLCJjbGllbnRfaWQiOiJkZjY2NjM4Yi0zZWY0LTQyMWYtYTE4ZS1lMjBkZWEzOGQ5N2QiLCJzY29wZXMiOiJ7XCJTY29wZVwiOlwiQ2hlY2tvdXRBcGlcIixcIkNsYWltc1wiOltdfSIsInJvbGUiOiJDaGVja291dEFwaSIsImlzcyI6Imh0dHBzOi8vYXV0aHNhbmRib3guYnJhc3BhZy5jb20uYnIiLCJhdWQiOiJVVlF4Y1VBMmNTSjFma1EzSVVFbk9pSTNkbTl0Zm1sNWVsQjVKVVV1UVdnPSIsImV4cCI6MTU2ODI5MTM0MCwibmJmIjoxNTY4MjA0OTQwfQ.J9uT3sCyPeLR1mM6zqiiWMeG2n79nbYU9wGgzoc-Rh8"

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
                getUrl == response.body()!!.shortUrl
            }
        })
    }
}