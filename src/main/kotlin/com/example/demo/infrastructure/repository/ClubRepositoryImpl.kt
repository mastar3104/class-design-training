package com.example.demo.infrastructure.repository

import com.example.demo.domain.entity.Club
import com.example.demo.domain.repository.ClubRepository
import com.example.demo.domain.value.ClubId
import com.example.demo.infrastructure.adapter.Database
import org.springframework.stereotype.Repository

@Repository
class ClubRepositoryImpl(
    private val database: Database,
): ClubRepository {
    override fun find(clubId: ClubId): Club? {
        return database.clubs.find { it["id"] == clubId.toString() }?.let { resource ->
            Club(
                id = clubId,
                name = resource["name"]!!,
                numberOfEmployee = resource["numberOfEmployee"]!!.toInt()
            )
        }
    }

    override fun save(club: Club) {
        val resource = mapOf(
            "id" to club.id.toString(),
            "name" to club.name,
            "numberOfEmployee" to club.numberOfEmployee.toString(),
        )
        database.clubAdd(resource)
    }
}