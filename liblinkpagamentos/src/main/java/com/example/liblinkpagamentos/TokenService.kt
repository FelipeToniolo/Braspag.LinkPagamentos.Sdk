package com.example.liblinkpagamentos

import android.content.Context
import com.example.liblinkpagamentos.network.CredentialsHttpClient

class TokenService(clientId: String, clientSecret: String) {
    private val clientId = clientId
    private val clientSecret = clientSecret

    fun getToken(
        onGetTokenCallback: (String) -> Unit,
        onErrorCallback: (String) -> Unit
    ) {
        val credentialsHttpClient = CredentialsHttpClient(clientId, clientSecret)
        credentialsHttpClient.getOAuthCredentials({ accessToken ->
            if (accessToken.isStillValid()) {
                onGetTokenCallback.invoke(accessToken.token)
            } else {
                //Todo: mandar ele tentar reemitir o token antes de dar o erro
                onErrorCallback.invoke("Token is not valid anymore")
            }
        }, { error ->
            onErrorCallback.invoke(error)
        })
    }
}