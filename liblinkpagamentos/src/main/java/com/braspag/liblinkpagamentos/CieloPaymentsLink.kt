package com.braspag.liblinkpagamentos

import com.braspag.liblinkpagamentos.models.RecurrentInterval
import com.braspag.liblinkpagamentos.models.SaleType
import com.braspag.liblinkpagamentos.models.ShippingType
import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkParameters
import com.braspag.liblinkpagamentos.models.paymentlink.Recurrent
import com.braspag.liblinkpagamentos.models.paymentlink.Shipping
import com.braspag.liblinkpagamentos.models.paymentlink.Transaction
import com.braspag.liblinkpagamentos.network.LinkPagamentosHttpClient
import com.braspag.liblinkpagamentos.service.TokenService

class CieloPaymentsLink(private val clientID: String, private val clientSecret: String) {

    fun generateLink(
        parameters: CieloPaymentsLinkParameters,
        callbacks: CieloPaymentsLinkCallbacks
    ) {
        val tokenCache = TokenService(clientID, clientSecret)
        tokenCache.getToken(callbacks,
            onGetTokenCallback = { token->
                generateLinkWithToken(parameters, token, callbacks)
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
        val recurrentInterval = mapRecurrentInterval(parameters)

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
            recurrent = Recurrent(
                interval = recurrentInterval,
                endDate = parameters.recurrentEndDate
            ),
            softDescriptor = parameters.softDescriptor
        )

        linkPagamentosHttpClient.getLink(model, token,
            onGetLinkCallback = callback::onGetLink,
            onErrorCallback = callback::onError
        )
    }

    private fun mapShippingType(parameters: CieloPaymentsLinkParameters): String {
        return when (parameters.shippingType){
            ShippingType.CORREIOS -> "Correios"
            ShippingType.FIXEDAMOUNT -> "FixedAmount"
            ShippingType.FREE -> "Free"
            ShippingType.WITHOUTSHIPPINGPICKUP -> "WithoutShippingPickUp"
            ShippingType.WITHOUTSHIPPING -> "WithoutShipping"
        }
    }

    private fun mapSaleType(parameters: CieloPaymentsLinkParameters): String {
        return when (parameters.type) {
            SaleType.ASSET -> "Asset"
            SaleType.DIGITAL -> "Digital"
            SaleType.SERVICE -> "Service"
            SaleType.PAYMENT -> "Payment"
            SaleType.RECURRENT-> "Recurrent"
        }
    }

    private fun mapRecurrentInterval(parameters: CieloPaymentsLinkParameters): String? {
        return when (parameters.recurrentInterval) {
            RecurrentInterval.MONTHLY -> "Monthly"
            RecurrentInterval.BIMONTHLY -> "Bimonthly"
            RecurrentInterval.QUARTERLY -> "Quarterly"
            RecurrentInterval.SEMIANNUAL -> "SemiAnnual"
            RecurrentInterval.ANNUAL -> "Annual"
            else -> null
        }
    }
}