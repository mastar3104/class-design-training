package com.example.demo.application.form.employee

data class CreateBody(
    val name: String,
    val teamId: String? = null,
    val clubId: String? = null,
)
