package com.example.demo.domain.repository

import com.example.demo.domain.entity.Club
import com.example.demo.domain.value.ClubId

interface ClubRepository {
    fun find(clubId: ClubId): Club?

    fun save(club: Club)
}