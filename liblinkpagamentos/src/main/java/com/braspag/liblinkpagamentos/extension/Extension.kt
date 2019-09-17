package com.braspag.liblinkpagamentos.extension

fun String.addBearerFormat(): String = "Bearer $this"