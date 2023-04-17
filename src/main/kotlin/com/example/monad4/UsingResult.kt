package com.example.monad4

import arrow.core.flatMap
import com.example.exception.CodeEnum
import com.example.exception.MyException
import java.math.BigDecimal

class UsingResultService {
    fun executeFoo(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        name.validateNullOrBlank("name")
            .flatMap { amount.validateNull("amount") }
            .flatMap { address.validateNullOrBlank("address") }
            .flatMap { age.validateNull("age") }
            .flatMap { amount!!.validateNonPositiveNumber("amount") }
            .flatMap { age!!.validateNegativeNumber("age") }
    }

    // batch
    fun executeFoo2(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val errorList: List<Throwable> = listOfNotNull(
            name.validateNullOrBlank("name").exceptionOrNull(),
            amount.validateNull("amount").exceptionOrNull(),
            address.validateNullOrBlank("address").exceptionOrNull(),
            age.validateNull("age").exceptionOrNull(),
        )

        if (errorList.isNotEmpty()) {
            val missingParameters = errorList.map { (it as MyFieldNameException).fieldName }
            throw MyException(CodeEnum.INVALID_PARAM, "missing parameters: ${missingParameters.joinToString(",")}")
        }

        amount!!.validateNonPositiveNumber("amount")
            .flatMap { age!!.validateNegativeNumber("age") }
    }
}

private fun Any?.validateNull(fieldName: String): Result<Unit> {
    if (this@validateNull == null)
        return Result.failure(MyFieldNameException(fieldName, CodeEnum.INVALID_PARAM, "'$fieldName' is missing"))
    return Result.success(Unit)
}

private fun String?.validateNullOrBlank(fieldName: String): Result<Unit> {
    if (this@validateNullOrBlank.isNullOrBlank())
        return Result.failure(MyFieldNameException(fieldName, CodeEnum.INVALID_PARAM, "'$fieldName' is missing"))
    return Result.success(Unit)
}

private fun BigDecimal.validateNegativeNumber(fieldName: String): Result<Unit> {
    if (this@validateNegativeNumber < BigDecimal.ZERO)
        return Result.failure(MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is negative"))
    return Result.success(Unit)
}

private fun BigDecimal.validateNonPositiveNumber(fieldName: String): Result<Unit> {
    if (this@validateNonPositiveNumber <= BigDecimal.ZERO)
        return Result.failure(MyException(CodeEnum.INVALID_PARAM, "'$fieldName' is 0 or negative"))
    return Result.success(Unit)
}

class MyFieldNameException(val fieldName: String, code: CodeEnum = CodeEnum.UNKNOWN, vararg args: String)
    : MyException(code, *args)