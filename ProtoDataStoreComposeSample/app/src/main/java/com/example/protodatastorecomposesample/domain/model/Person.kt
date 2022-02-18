package com.example.protodatastorecomposesample.domain.model

internal data class Person(
    val id: String,
    val name: String,
    val height: String,
    val student: Boolean,
    val phoneNumber: String,
    val phoneTypeName: String
)
