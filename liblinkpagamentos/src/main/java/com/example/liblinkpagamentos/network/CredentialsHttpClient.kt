package com.example.liblinkpagamentos.network

import com.example.liblinkpagamentos.models.AccessToken
import com.example.liblinkpagamentos.models.auth.AuthClientModel
import com.example.liblinkpagamentos.models.auth.OAuthApi
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CredentialsHttpClient(private val clientId: String, private val clientSecret: String) {

    fun getOAuthCredentials(
        onSuccessCallback: (model: AccessToken) -> Unit,
        onError: (error: String) -> Unit
    ) {
        val authorizationHeaderValue = Credentials.basic(clientId, clientSecret)

        val webClient = WebClient("https://meucheckoutsandbox.braspag.com.br/api/public/")
        val call = webClient.createService(OAuthApi::class.java)
            .getTokenOAuth(authorizationHeaderValue, "client_credentials")

        call.enqueue(object : Callback<AuthClientModel> {
            override fun onFailure(call: Call<AuthClientModel>, t: Throwable) {
                t.message?.let { onError.invoke(it) }
            }

            override fun onResponse(call: Call<AuthClientModel>, response: Response<AuthClientModel>) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        onSuccessCallback(AccessToken(accessToken, expiresIn, Calendar.getInstance().time))
                    }
                    if (response.body() == null)
                        onError.invoke("The response object is null.")
                } else {
                    onError.invoke(response.message())
                }
            }
        })
    }
}
