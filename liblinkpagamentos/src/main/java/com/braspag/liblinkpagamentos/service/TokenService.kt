package com.braspag.liblinkpagamentos.service

import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkCallbacks
import com.braspag.liblinkpagamentos.models.auth.AccessToken
import com.braspag.liblinkpagamentos.network.CredentialsHttpClient

class TokenService(
    private val environment: Environment,
    private val clientId: String,
    private val clientSecret: String
) {

    fun getToken(
        callbacks: CieloPaymentsLinkCallbacks,
        onGetTokenCallback: (String) -> Unit,
        onErrorCallback: (String) -> Unit
    ) {
        val credentialsHttpClient = CredentialsHttpClient(environment, clientId, clientSecret)
        credentialsHttpClient.getOAuthCredentials({ accessToken ->
            if (accessToken.isStillValid()) {
                onGetTokenCallback.invoke(accessToken.token)
            } else {
                onErrorCallback.invoke("Token is not valid anymore")

                refreshToken(
                    onGetTokenCallback = {
                        onGetTokenCallback.invoke(accessToken.token)
                    },
                    onErrorCallback = callbacks::onError
                )
            }
        }, { error ->
            onErrorCallback.invoke(error)
        })
    }

    fun refreshToken(
        onGetTokenCallback: (String) -> Unit,
        onErrorCallback: (String) -> Unit
    ) {
        val credentialsHttpClient = CredentialsHttpClient(environment, clientId, clientSecret)
        credentialsHttpClient.getOAuthCredentials({ accessToken: AccessToken ->
            onGetTokenCallback.invoke(accessToken.token)
        }, {
            onErrorCallback.invoke("Token cannot be generated")
        })
    }
}