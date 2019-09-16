package com.example.braspaglinkpagamentossdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.liblinkpagamentos.CieloPaymentsLinkParameters
import com.example.liblinkpagamentos.CieloPaymentsLink
import com.example.liblinkpagamentos.CieloPaymentsLinkCallbacks
import com.example.liblinkpagamentos.models.SaleType
import com.example.liblinkpagamentos.models.ShippingType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var paymentsLink: CieloPaymentsLink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paymentsLink = CieloPaymentsLink(
            clientID = "df66638b-3ef4-421f-a18e-e20dea38d97d",
            clientSecret = "q13XZ48haFg4EhAS2cjcoyX7OzRECYysY6T9TJLmKNM="
        )
        val parameters = CieloPaymentsLinkParameters(
            "Pedido", "4000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
            "teste", "1000000000"
        )

        paymentsLink.generateLink(parameters, object : CieloPaymentsLinkCallbacks {
            override fun onGetLink(link: String) {
                txt_teste.text = link
            }

            override fun onError(error: String) {
                txt_teste.text = "Deu pau!"
            }
        })
    }
}
