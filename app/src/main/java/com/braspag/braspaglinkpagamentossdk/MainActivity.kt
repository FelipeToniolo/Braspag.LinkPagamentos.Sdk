package com.braspag.braspaglinkpagamentossdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkParameters
import com.braspag.liblinkpagamentos.CieloPaymentsLink
import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkCallbacks
import com.braspag.liblinkpagamentos.models.paymentlink.recurrent.RecurrentInterval
import com.braspag.liblinkpagamentos.models.paymentlink.SaleType
import com.braspag.liblinkpagamentos.models.paymentlink.shipping.ShippingType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paymentsLink = CieloPaymentsLink(
            clientID = "df66638b-3ef4-421f-a18e-e20dea38d97d",
            clientSecret = "q13XZ48haFg4EhAS2cjcoyX7OzRECYysY6T9TJLmKNM="
        )
        val parameters = CieloPaymentsLinkParameters(
            "Pedido", "4000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
            "teste", "1000000000", recurrentInterval = RecurrentInterval.MONTHLY
        )

        val parameters2 = CieloPaymentsLinkParameters(
            "Pedido", "10000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
            "teste", "1000000000"
        )


        paymentsLink.generateLink(parameters, object :
            CieloPaymentsLinkCallbacks {
            override fun onGetLink(link: String) {
                txt1.text = link
            }

            override fun onError(error: String) {
                txt1.text = "Deu pau!"
            }
        })

        paymentsLink.generateLink(parameters2, object :
            CieloPaymentsLinkCallbacks {
            override fun onGetLink(link: String) {
                txt2.text = link
            }

            override fun onError(error: String) {
                txt2.text = "Deu pau!"
            }
        })
    }
}
