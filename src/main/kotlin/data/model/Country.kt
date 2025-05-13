package data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: Name,
    val capital: List<String> = emptyList(),
    val flags: Flags,
    val population: Long,
    val area: Double
)

@Serializable
data class Name(
    val common: String
)

@Serializable
data class Flags(
    val png: String
)
