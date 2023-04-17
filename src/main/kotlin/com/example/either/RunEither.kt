package com.example.either

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.continuations.ensureNotNull
import arrow.core.flatMap
import java.util.*
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val engineService = EngineService()
    validateAndBuildTradeRequest(1, "BTC", 0.001)
        .flatMap { engineService.sendWallet(it) }
        .flatMap { engineService.awaitNotification(it.trackingId, it.username) }
        .fold({ errorMsg ->
              throw MyException(errorMsg)
        }, { response ->
//            sendUser(SuccessResponse(orderId = response.orderId))
        })
}

private class MyException(message: String, cause: Throwable? = null) : Exception(message, cause)

private class EngineService {
    fun sendWallet(request: TradeRequest): Either<String, WalletPacket> {
        return Either.Right(WalletPacket(UUID.randomUUID().toString(), request.username))
    }

    fun awaitNotification(trackingId: String, username: String): Either<String, SuccessResponse> {
        return Either.Right(SuccessResponse(UUID.randomUUID().toString()))
    }
}

private suspend fun validateAndBuildTradeRequest(uid: Int?, currency: String?, amount: Double?): Either<String, TradeRequest> {
    val userRepo = UserRepo()
    val coinRepo = CoinRepo()
    return either {
        ensure(uid != null && uid > 0) { "'uid' is mandatory parameter" }
        ensure(!currency.isNullOrBlank()) { "'currency' is mandatory parameter" }
        ensure(amount != null && amount > 0.0) { "'amount' is mandatory parameter" }

        val user: AuthUser = userRepo.getAndValidateUser(uid!!, UserPermission.TRADE).bind()
        val sysCoinType: SysCoinType = ensureNotNull(coinRepo.getSysCoinType(currency!!)) {
            "unknown currency $currency"
        }
        ensure(coinRepo.allowTrade(sysCoinType)) { "$currency 'trade' action not support" }
        return@either TradeRequest(
            username = user.username,
            coinId = sysCoinType.id,
            amount = amount!!
        )
    }
}

private class UserRepo {
    fun getAndValidateUser(uid: Int, permission: UserPermission): Either<String, AuthUser> {
        val user: AuthUser = mapping[uid]
            ?: return Either.Left("user not found, id: $uid")

        return if (user.permissions.contains(permission)) {
            Either.Right(user)
        } else {
            Either.Left("'$permission' action not support")
        }
    }
    companion object {
        private val mapping = mapOf(
            1 to AuthUser(1, "alice"),
            2 to AuthUser(2, "john", setOf(UserPermission.DEPOSIT, UserPermission.WITHDRAW)),
            3 to AuthUser(3, "tommy", emptySet())
        )
    }
}

private class CoinRepo {
    fun getSysCoinType(currency: String): SysCoinType? = mapping[currency]
    fun allowTrade(sysCoinType: SysCoinType): Boolean {
        return sysCoinType.allowTrade
    }
    companion object {
        private val mapping = mapOf(
            "BTC" to SysCoinType("BTC", 1),
            "ETH" to SysCoinType("ETH", 2),
            "LTC" to SysCoinType("LTC", 3, false)
        )
    }
}

private data class ErrorResponse(
    val errorMessage: String
)

private data class SuccessResponse(
    val orderId: String
)

private data class WalletPacket(
    val trackingId: String,
    val username: String
)

private data class TradeRequest(
    val username: String,
    val coinId: Int,
    val amount: Double
)

private data class SysCoinType(
    val shortName: String,
    val id: Int,
    val allowTrade: Boolean = true
)

private data class AuthUser(
    val id: Int,
    val username: String,
    val permissions: Set<UserPermission> = UserPermission.values().toSet(),
    val quoteCurrency: String = "USD"
)

private enum class UserPermission {
    TRADE,
    DEPOSIT,
    WITHDRAW
}
