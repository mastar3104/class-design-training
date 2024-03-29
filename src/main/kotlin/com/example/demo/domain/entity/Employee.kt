package com.example.demo.domain.entity

import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import com.example.demo.domain.value.TeamId

data class Employee(
    val id: EmployeeId,
    val name: String,
    val teamId: TeamId,
    val clubId: ClubId,
    val clubApproval: Boolean
) {
    companion object {
        fun create(
            name: String,
            teamId: TeamId?,
            clubId: ClubId?,
        ): Employee {
            return Employee(
                id = EmployeeId.create(),
                name = name,
                teamId = teamId ?: TeamId.DEFAULT,
                clubId = clubId ?: ClubId.NOT_BELONG,
                clubApproval = false,
            )
        }
    }
}
