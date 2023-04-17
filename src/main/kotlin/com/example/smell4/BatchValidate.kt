package com.example.smell4

import com.example.exception.CodeEnum
import com.example.exception.MyException
import java.math.BigDecimal

class FooService {
    fun executeFoo(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val missingParameters = listOfNotNull(
            name.findNullOrBlankFieldName("name"),
            amount.findNullFieldName("amount"),
            address.findNullOrBlankFieldName("address"),
            age.findNullFieldName("age")
        )

        if (missingParameters.isNotEmpty())
            throw MyException(CodeEnum.INVALID_PARAM, "missing parameters: ${missingParameters.joinToString(",")}")

        amount!!.validateNonPositiveNumber("amount")
        age!!.validateNegativeNumber("age")
    }
}

private fun Any?.findNullFieldName(fieldName: String): String? = fieldName.takeIf { this@findNullFieldName == null }

private fun String?.findNullOrBlankFieldName(fieldName: String): String? = fieldName.takeIf { this@findNullOrBlankFieldName.isNullOrBlank() }

//private fun BigDecimal.findNegativeNumber(fieldName: String): String? = fieldName.takeIf { this@findNegativeNumber < BigDecimal.ZERO }
//
//private fun BigDecimal.findNonPositiveNumber(fieldName: String): String? = fieldName.takeIf { this@findNonPositiveNumber <= BigDecimal.ZERO }