package com.example.liblinkpagamentos

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.liblinkpagamentos.helper.PreferencesHelper
import com.example.liblinkpagamentos.network.CredentialsHttpClient
import java.util.Base64
import kotlin.coroutines.coroutineContext

class PaymentsLink (activity:Context){

    private val context = activity

    @RequiresApi(Build.VERSION_CODES.O)
    fun AuthenticateCredentials( clientID: String, clientSecret: String) {
        val clientId_clientSecret = "$clientID:$clientSecret"

        var base64format = Base64.getEncoder().encodeToString(clientId_clientSecret.toByteArray())
        CredentialsHttpClient().getOAuthCredentials(context, base64format)
    }

    fun GenerateLink(
        name: String, price: Long, type: String, shippingType: String, shippingName: String,
        shippingPrice: Long, showDescription: String? = null, expirationDate: Long? = null, weight: Long? = null,
        softDescriptor: String? = null, maxNumberOfInstallments: Int? = null, shippingOriginZipCode: Long? = null
    ): String? {

        val token = PreferencesHelper.get(context, "AUTH")

        return token
    }
}