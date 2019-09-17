package com.braspag.liblinkpagamentos.models.paymentlink

import com.braspag.liblinkpagamentos.models.RecurrentInterval
import com.braspag.liblinkpagamentos.models.SaleType
import com.braspag.liblinkpagamentos.models.ShippingType

data class CieloPaymentsLinkParameters @JvmOverloads constructor(
    val name: String,
    val price: String,
    val type: SaleType,
    val shippingType: ShippingType,
    val shippingName: String,
    val shippingPrice: String,
    val description: String? = null,
    val showDescription: String? = null,
    val expirationDate: String? = null,
    val recurrentInterval: RecurrentInterval? = null,
    val recurrentEndDate: String? = null,
    val sku: String? = null,
    val weight: Int? = null,
    val softDescriptor: String? = null,
    val maxNumberOfInstallments: Int? = null,
    val shippingOriginZipCode: String? = null
)