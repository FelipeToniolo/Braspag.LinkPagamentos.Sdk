package com.example.liblinkpagamentos

import com.example.liblinkpagamentos.models.SaleType
import com.example.liblinkpagamentos.models.ShippingType
import com.example.liblinkpagamentos.models.linkpagamentos.Shipping
import com.example.liblinkpagamentos.models.linkpagamentos.Transaction
import com.example.liblinkpagamentos.network.LinkPagamentosHttpClient

class CieloPaymentsLink(clientID: String, clientSecret: String) {

    private val clientID = clientID
    private val clientSecret = clientSecret

    fun generateLink(
        parameters: CieloPaymentsLinkParameters,
        callbacks: CieloPaymentsLinkCallbacks
    ) {
        val tokenCache = TokenService(clientID, clientSecret)
        tokenCache.getToken(
            onGetTokenCallback = {
                generateLinkWithToken(parameters, it, callbacks)
            },
            onErrorCallback = callbacks::onError
        )
    }

    private fun generateLinkWithToken(
        parameters: CieloPaymentsLinkParameters,
        token: String,
        callback: CieloPaymentsLinkCallbacks
    ) {
        val linkPagamentosHttpClient = LinkPagamentosHttpClient()

        val saleType = mapSaleType(parameters)
        val shippingType = mapShippingType(parameters)

        val model = Transaction(
            type = saleType,
            name = parameters.name,
            description = parameters.description,
            price = parameters.price,
            weight = parameters.weight,
            expirationDate = parameters.expirationDate,
            maxNumberOfInstallments = parameters.maxNumberOfInstallments,
            showDescription = parameters.showDescription,
            sku = parameters.sku,
            shipping = Shipping(
                type = shippingType,
                name = parameters.shippingName,
                price = parameters.shippingPrice,
                originZipCode = parameters.shippingOriginZipCode
            ),
            softDescriptor = parameters.softDescriptor
        )

        linkPagamentosHttpClient.getLink(model, token,
            onGetLinkCallback = callback::onGetLink,
            onErrorCallback = callback::onError
        )
    }

    private fun mapShippingType(parameters: CieloPaymentsLinkParameters): String {
        return when {
            parameters.shippingType == ShippingType.CORREIOS -> "Correios"
            parameters.shippingType == ShippingType.FIXEDAMOUNT -> "FixedAmount"
            parameters.shippingType == ShippingType.FREE -> "Free"
            parameters.shippingType == ShippingType.WITHOUTSHIPPINGPICKUP -> "WithoutShippingPickUp"
            else -> "WithoutShipping"
        }
    }

    private fun mapSaleType(parameters: CieloPaymentsLinkParameters): String {
        return when (parameters.type) {
            SaleType.ASSET -> "Asset"
            SaleType.DIGITAL -> "Digital"
            SaleType.SERVICE -> "Service"
            SaleType.PAYMENT -> "Payment"
            else -> "Recurrent"
        }
    }
}