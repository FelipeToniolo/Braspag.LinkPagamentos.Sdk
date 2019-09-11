package com.example.liblinkpagamentos

import android.content.Context
import com.example.liblinkpagamentos.helper.PreferencesHelper
import com.example.liblinkpagamentos.models.SaleType
import com.example.liblinkpagamentos.models.ShippingType
import com.example.liblinkpagamentos.models.linkpagamentos.TransactionResponse
import com.example.liblinkpagamentos.models.linkpagamentos.Shipping
import com.example.liblinkpagamentos.models.linkpagamentos.Transaction
import com.example.liblinkpagamentos.network.LinkPagamentosHttpClient

class CieloPaymentsLink(activity: Context, clientID: String, clientSecret: String) {

    private val context = activity
    private val clientID = clientID
    private val clientSecret = clientSecret
    private val authenticateCredentials = AuthenticateCredentials()

    private fun AuthenticateCredentials() {

        val tokenCache = TokenService(context, clientID, clientSecret)
        tokenCache.getToken()
    }

    fun GenerateLink(
        name: String,
        price: String,
        type: SaleType,
        shippingType: ShippingType,
        shippingName: String,
        shippingPrice: String,
        description: String? = null,
        showDescription: String? = null,
        expirationDate: String? = null,
        sku: String? = null,
        weight: Int? = null,
        softDescriptor: String? = null,
        maxNumberOfInstallments: Int? = null,
        shippingOriginZipCode: String? = null
    ): String? {

        authenticateCredentials

        val linkPagamentosHttpClient = LinkPagamentosHttpClient()

        val saleType = when (type) {
            SaleType.ASSET -> "Asset"
            SaleType.DIGITAL -> "Digital"
            SaleType.SERVICE -> "Service"
            SaleType.PAYMENT -> "Payment"
            else -> "Recurrent"
        }

        val shippingType = when {
            shippingType == ShippingType.CORREIOS -> "Correios"
            shippingType == ShippingType.FIXEDAMOUNT -> "FixedAmount"
            shippingType == ShippingType.FREE -> "Free"
            shippingType == ShippingType.WITHOUTSHIPPINGPICKUP -> "WithoutShippingPickUp"
            else -> "WithoutShipping"
        }



        val model = Transaction(
            type = saleType,
            name = name,
            description = description,
            price = price,
            weight = weight,
            expirationDate = expirationDate,
            maxNumberOfInstallments = maxNumberOfInstallments,
            showDescription = showDescription,
            sku = sku,
            shipping = Shipping(
                type = shippingType,
                name = shippingName,
                price = shippingPrice,
                originZipCode = shippingOriginZipCode
            ),
            softDescriptor = softDescriptor
        )
        linkPagamentosHttpClient.getLink(context, model)

        val url = TransactionResponse().url

        return url
    }
}