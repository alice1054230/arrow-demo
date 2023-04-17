package com.example.monad4

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.continuations.ensureNotNull
import java.math.BigDecimal

class UsingArrowService {
    fun executeFoo(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val validateResult: Either<ParameterError, Unit> = either.eager {
            name.validateNullOrBlank("name").bind()
            amount.validateNull("amount").bind()
            address.validateNullOrBlank("address").bind()
            age.validateNull("age").bind()

            amount!!.validateNonPositiveNumber("amount").bind()
            age!!.validateNegativeNumber("age").bind()
        }


        (validateResult as? Either.Left)?.value?.let {
            // do error handling
        }
    }

    // batch
    fun executeFoo1(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val validateResult: Either<String, Unit> = either.eager {
            val errorList: List<ParameterError> = listOfNotNull(
                name.validateNullOrBlank("name").swap().orNull(),
                amount.validateNull("amount").swap().orNull(),
                address.validateNullOrBlank("address").swap().orNull(),
                age.validateNull("age").swap().orNull()
            )
            ensure(errorList.isNotEmpty()) {
                "missing parameters: ${errorList.map(ParameterError::fieldName).joinToString(",")}"
            }

            amount!!.validateNonPositiveNumber("amount").mapLeft(ParameterError::message).bind()
            age!!.validateNegativeNumber("age").mapLeft(ParameterError::message).bind()
        }


        (validateResult as? Either.Left)?.value?.let {
            // do error handling
        }
    }

    fun executeFoo2(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val validateResult: Either<String, Unit> = either.eager {
            ensureNotNull(name) { "'name' is missing" }
            val amount : BigDecimal = ensureNotNull(amount) { "'amount' is missing" }
            ensureNotNull(address) { "'address' is missing" }
            val age: BigDecimal = ensureNotNull(age) { "'age' is missing" }

            ensure(amount > BigDecimal.ZERO) { "'amount' is 0 or negative" }
            ensure(age >= BigDecimal.ZERO) { "'age' is negative" }
        }

        (validateResult as? Either.Left)?.value?.let {
            // do error handling
        }
    }

    companion object {
        private const val PARAM_MISSING_MSG = "'%s' is missing"
        private const val PARAM_POSITIVE_CHECK_FAIL_MSG = "'%s' is 0 or negative"
        private const val PARAM_NON_NEGATIVE_CHECK_FAIL_MSG = "'%s' is negative"
    }
    fun executeFoo3(name: String?, amount: BigDecimal?, address: String?, age: BigDecimal?) {
        val validateResult: Either<String, Unit> = either.eager {
            ensureNotNull(name) { PARAM_MISSING_MSG.format("name") }
            val amount : BigDecimal = ensureNotNull(amount) { PARAM_MISSING_MSG.format("amount") }
            ensureNotNull(address) { PARAM_MISSING_MSG.format("address") }
            val age: BigDecimal = ensureNotNull(age) { PARAM_MISSING_MSG.format("age") }

            ensure(amount > BigDecimal.ZERO) { PARAM_POSITIVE_CHECK_FAIL_MSG.format("amount") }
            ensure(age >= BigDecimal.ZERO) { PARAM_NON_NEGATIVE_CHECK_FAIL_MSG.format("age") }
        }

        (validateResult as? Either.Left)?.value?.let {
            // do error handling
        }
    }
}

private fun Any?.validateNull(fieldName: String): Either<ParameterError, Unit> = either.eager {
    ensureNotNull(this@validateNull) { ParameterError(fieldName, "'$fieldName' is missing") }
}

private fun String?.validateNullOrBlank(fieldName: String): Either<ParameterError, Unit> = either.eager {
    ensureNotNull(this@validateNullOrBlank.isNullOrBlank()) { ParameterError(fieldName, "'$fieldName' is missing") }
}

private fun BigDecimal.validateNegativeNumber(fieldName: String): Either<ParameterError, Unit> = either.eager {
    ensureNotNull(this@validateNegativeNumber < BigDecimal.ZERO) { ParameterError(fieldName, "'$fieldName' is negative") }
}

private fun BigDecimal.validateNonPositiveNumber(fieldName: String): Either<ParameterError, Unit> = either.eager {
    ensureNotNull(this@validateNonPositiveNumber <= BigDecimal.ZERO) { ParameterError(fieldName, "'$fieldName' is 0 or negative") }
}

data class ParameterError(
    val fieldName: String,
    val message: String
)