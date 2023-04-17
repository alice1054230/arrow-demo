package com.example.nullable

import arrow.core.Option
import arrow.core.continuations.nullable
import arrow.core.none
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    var name: String?
    while (true) {
        println("Who is speaker?")
        name = readLine()
        if (name == "end") break
        val city = nullable {
            val speaker = Speaker(name)
            println("1. prepared speaker: $speaker")
            val talk = speaker.nextTalk().bind()
            println("2. getConference from talk: $talk")
            val conference = talk.getConference().bind()
            println("3. getCity from conference: $conference")
            val city = conference.getCity().bind()
            return@nullable city
        }
        println("city: $city")
    }
}

private class Speaker(
    val name: String?
) {
    companion object {
        private val mapping = mapOf(
            "Alice" to Talk("Talk A"),
            "Joe" to Talk("Talk B"),
            "Mandy" to Talk("Talk C"),
            "Frank" to Talk("Talk D"),
            "Bob" to Talk("Talk E")
        )
    }
    fun nextTalk(): Option<Talk> {
        return mapping[this.name]?.let { Option(it) } ?: none()
    }
}

private data class Talk(
    val name: String
) {
    companion object {
        private val mapping = mapOf(
            Talk("Talk A") to Conference("Conference A"),
            Talk("Talk B") to Conference("Conference B"),
            Talk("Talk C") to Conference("Conference C"),
            Talk("Talk E") to Conference("Conference D")
        )
    }
    fun getConference(): Option<Conference> {
        return mapping[this]?.let { Option(it) } ?: none()
    }
}

private data class Conference(
    val name: String
) {
    companion object {
        private val mapping = mapOf(
            Conference("Conference A") to City("Taipei"),
            Conference("Conference B") to City("Taoyuan"),
            Conference("Conference C") to City("Kaohsiung")
        )
    }
    fun getCity(): Option<City> {
        return mapping[this]?.let { Option(it) } ?: none()
    }
}

private data class City(
    val name: String
)