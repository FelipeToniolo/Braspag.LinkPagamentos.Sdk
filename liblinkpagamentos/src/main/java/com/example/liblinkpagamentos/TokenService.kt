package com.example.liblinkpagamentos

import android.content.Context
import android.os.Build
import com.example.liblinkpagamentos.helper.PreferencesHelper
import com.example.liblinkpagamentos.models.auth.AuthClientModel
import com.example.liblinkpagamentos.network.CredentialsHttpClient
import java.time.LocalDateTime
import java.util.*

class TokenService(context: Context, clientId: String, clientSecret: String) {
    private val context = context
    private var authToken: String? = null
    private var authCredential: AuthClientModel? = null
    private val authKey = "AUTH_TOKEN"
    private val clientId = clientId
    private val clientSecret = clientSecret
    private var token = PreferencesHelper.get(context, "TOKEN")
    private var expireIn = PreferencesHelper.get(context, "TOKENEXPIRE")

    fun getToken() {
//            if (TokenService(context, clientId, clientSecret).validateToken(token, expireIn) == false){
        val context = context
        val clientId_clientSecret = "$clientId:$clientSecret"
        val credentialsHttpClient = CredentialsHttpClient()

        var base64format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(clientId_clientSecret.toByteArray())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        credentialsHttpClient.getOAuthCredentials(context, base64format)
//            } else
//                PreferencesHelper.get(context,"TOKEN")

    }

//    private fun validateToken(token: String?, expireIn:String?) : Boolean {
//
//        val dateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) LocalDateTime.now()
//        else TODO("VERSION.SDK_INT < O")
//
//        if (token.isNullOrEmpty())
//            false
//    }
}