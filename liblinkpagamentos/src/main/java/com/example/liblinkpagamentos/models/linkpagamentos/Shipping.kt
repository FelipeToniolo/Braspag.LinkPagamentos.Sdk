package com.example.liblinkpagamentos.models.linkpagamentos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Shipping(
    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("price")
    @Expose
    var price: String?,

    @SerializedName("originZipCode")
    @Expose
    var originZipCode: String? = null,

    @SerializedName("type")
    @Expose
    var type: String?)

