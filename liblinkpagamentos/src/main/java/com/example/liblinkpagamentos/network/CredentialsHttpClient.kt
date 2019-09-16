package com.example.liblinkpagamentos.network

import android.content.Context
import com.example.liblinkpagamentos.extension.addBasicFormat
import com.example.liblinkpagamentos.extension.toStatusCode
import com.example.liblinkpagamentos.models.ClientResultModel
import com.example.liblinkpagamentos.models.HttpStatusCode
import com.example.liblinkpagamentos.models.auth.AuthClientModel
import com.example.liblinkpagamentos.models.auth.OAuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CredentialsHttpClient {

    fun getOAuthCredentials(context: Context, base64: String, callback: (String) -> Unit) {

        val authorizationFormat = base64.addBasicFormat()

        val webClient =
            WebClient("https://meucheckoutsandbox.braspag.com.br/api/public/")
        val call = webClient.createService(OAuthApi::class.java)
            .getTokenOAuth(authorizationFormat, "client_credentials")

        call.enqueue(object : Callback<AuthClientModel> {
            override fun onFailure(call: Call<AuthClientModel>, t: Throwable) {
                ClientResultModel(
                    null,
                    HttpStatusCode.Unknown
                )
            }

            override fun onResponse(call: Call<AuthClientModel>, response: Response<AuthClientModel>) {
                if (response.isSuccessful) {
                    ClientResultModel(
                        result = response.body(),
                        statusCode = response.code().toStatusCode()
                    )

                    callback.invoke(response.body()!!.accessToken)

//                    TransactionResponse().token = response.body()!!.accessToken

//                    PreferencesHelper.set(context, "TOKEN", response.body()!!.accessToken)
                } else
                    ClientResultModel(
                        null,
                        response.code().toStatusCode(),
                        response.errorBody()?.string()
                    )
            }
        })
    }
}
