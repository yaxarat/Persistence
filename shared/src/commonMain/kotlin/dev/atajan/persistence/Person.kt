package dev.atajan.persistence

data class Person(
    val id: Int? = null,
    val name: String,
    val email: String,
    val phone: String,
    val ssn: Int,
) {
    constructor(
        name: String,
        email: String,
        phone: String,
        ssn: Int
    ): this (
        id = null,
        name = name,
        email = email,
        phone = phone,
        ssn = ssn
    )
}
