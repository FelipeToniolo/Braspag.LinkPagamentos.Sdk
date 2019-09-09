package com.example.braspaglinkpagamentossdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.liblinkpagamentos.PaymentsLink

class MainActivity : AppCompatActivity() {

    lateinit var paymentsLink : PaymentsLink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paymentsLink = PaymentsLink(this)

        paymentsLink.AuthenticateCredentials("df66638b-3ef4-421f-a18e-e20dea38d97d",
            "q13XZ48haFg4EhAS2cjcoyX7OzRECYysY6T9TJLmKNM=")

        paymentsLink.GenerateLink("",1,"","",
            "",1)

    }
}
