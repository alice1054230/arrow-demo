package com.example.exception

open class MyException(code: Int, message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(code: CodeEnum = CodeEnum.UNKNOWN, cause: Throwable? = null) : this(code.code, code.message, cause)
    constructor(code: CodeEnum = CodeEnum.UNKNOWN, vararg args: String) : this(code.code, code.message.format(*args), null)
}

enum class CodeEnum(val code: Int, val message: String) {
    UNKNOWN(0, "unknown error"),
    INVALID_PARAM(1, "Parameter invalid, cause of %s")
}
