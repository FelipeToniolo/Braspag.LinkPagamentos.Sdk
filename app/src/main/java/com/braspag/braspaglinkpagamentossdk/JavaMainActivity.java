package com.braspag.braspaglinkpagamentossdk;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.braspag.liblinkpagamentos.CieloPaymentsLink;
import com.braspag.liblinkpagamentos.Environment;
import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkCallbacks;
import com.braspag.liblinkpagamentos.models.paymentlink.CieloPaymentsLinkParameters;
import com.braspag.liblinkpagamentos.models.paymentlink.SaleType;
import com.braspag.liblinkpagamentos.models.paymentlink.recurrent.RecurrentInterval;
import com.braspag.liblinkpagamentos.models.paymentlink.shipping.ShippingType;
import org.jetbrains.annotations.NotNull;

public class JavaMainActivity extends AppCompatActivity {
    private TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txt1 = this.findViewById(R.id.txt1);
        CieloPaymentsLink paymentsLink = new CieloPaymentsLink(Environment.SANDBOX,"df66638b-3ef4-421f-a18e-e20dea38d97d",
                "q13XZ48haFg4EhAS2cjcoyX7OzRECYysY6T9TJLmKNM=");

        CieloPaymentsLinkParameters parameters = new CieloPaymentsLinkParameters(
                "Pedido", "4000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
                "teste", "1000000000");

        CieloPaymentsLinkParameters parameters2 = new CieloPaymentsLinkParameters(
                "Pedido", "4000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
                "teste", "1000000000");

        paymentsLink.generateLink(parameters, new CieloPaymentsLinkCallbacks() {
            @Override
            public void onGetLink(@NotNull String link) {
                String meuLink = link;
                txt1.setText(meuLink);
            }

            @Override
            public void onError(@NotNull String error) {
                txt1.setText("Deu pau!");
            }
        });
    }
}
