package com.example.smell4

import com.example.exception.CodeEnum
import com.example.exception.MyException
import java.math.BigDecimal

class NormalValidateService {
    fun executeFoo(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        if (name.isNullOrBlank())
            throw MyException(CodeEnum.INVALID_PARAM, "'name' is missing")

        if (amount == null)
            throw MyException(CodeEnum.INVALID_PARAM, "'amount' is missing")

        if (address.isNullOrBlank())
            throw MyException(CodeEnum.INVALID_PARAM, "'address' is missing")

        if (age == null)
            throw MyException(CodeEnum.INVALID_PARAM, "'age' is missing")

        if (amount <= BigDecimal.ZERO)
            throw MyException(CodeEnum.INVALID_PARAM, "'amount' is 0 or negative")

        if (age < BigDecimal.ZERO)
            throw MyException(CodeEnum.INVALID_PARAM, "'age' is negative")
    }

    fun executeFoo2(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        name.validateNullOrBlank("name")
        amount.validateNull("amount")
        address.validateNullOrBlank("address")
        age.validateNull("age")

        amount!!.validateNonPositiveNumber("amount")
        age!!.validateNegativeNumber("age")
    }
}

private fun Any?.validateNull(fieldName: String) {
    if (this@validateNull == null)
        throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is missing")
}

private fun String?.validateNullOrBlank(fieldName: String) {
    if (this@validateNullOrBlank.isNullOrBlank())
        throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is missing")
}

fun BigDecimal.validateNegativeNumber(fieldName: String) {
    if (this@validateNegativeNumber < BigDecimal.ZERO)
        throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is negative")
}

fun BigDecimal.validateNonPositiveNumber(fieldName: String) {
    if (this@validateNonPositiveNumber <= BigDecimal.ZERO)
        throw MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is 0 or negative")
}