package com.example.liblinkpagamentos.network

import android.content.Context
import android.util.Log
import com.example.liblinkpagamentos.extension.addBasicFormat
import com.example.liblinkpagamentos.helper.PreferencesHelper
import com.example.liblinkpagamentos.models.AuthClientModel
import com.example.liblinkpagamentos.models.OAuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CredentialsHttpClient {

   fun getOAuthCredentials(context: Context, base64: String) {

        val authorizationFormat = base64.addBasicFormat()

        val webClient =
            WebClient("https://meucheckoutsandbox.braspag.com.br/api/public/")
        val call = webClient.createService(OAuthApi::class.java)
            .getTokenOAuth(authorizationFormat, "client_credentials")

        call.enqueue(object : Callback<AuthClientModel> {
           override fun onResponse(call: Call<AuthClientModel>, response: Response<AuthClientModel>) {

                if (response.isSuccessful) {
                    PreferencesHelper.set(context, "AUTH", response.body()!!.accessToken)
                } else {
                    Log.e("Braspag", "Credenciais Invalidas")

                }
            }
            override fun onFailure(call: Call<AuthClientModel>, t: Throwable) {
            }
        })
    }
}