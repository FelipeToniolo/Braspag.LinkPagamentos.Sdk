package com.example.braspaglinkpagamentossdk;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.liblinkpagamentos.CieloPaymentsLink;
import com.example.liblinkpagamentos.CieloPaymentsLinkCallback;
import com.example.liblinkpagamentos.models.SaleType;
import com.example.liblinkpagamentos.models.ShippingType;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public class JavaMainActivity extends AppCompatActivity {
    private TextView txt_teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txt_teste = this.findViewById(R.id.txt_teste);
        CieloPaymentsLink paymentsLink = new CieloPaymentsLink(this, "df66638b-3ef4-421f-a18e-e20dea38d97d", "q13XZ48haFg4EhAS2cjcoyX7OzRECYysY6T9TJLmKNM=");

        paymentsLink.generateLink(
                "Pedido", "4000", SaleType.DIGITAL, ShippingType.WITHOUTSHIPPING,
                "teste", "1000000000", new CieloPaymentsLinkCallback() {
                    @Override
                    public void onGetLink(@NotNull String link) {
                        String meuLink = link;
                        txt_teste.setText(meuLink);
                    }

                    @Override
                    public void onError(@NotNull String error) {
                        txt_teste.setText("Deu pau!");
                    }
                });

    }
}
