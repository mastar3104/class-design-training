package com.example.demo.domain.entity

import com.example.demo.domain.value.ClubId

data class Club(
    val id: ClubId,
    val name: String,
    val numberOfEmployee: Int,
)
