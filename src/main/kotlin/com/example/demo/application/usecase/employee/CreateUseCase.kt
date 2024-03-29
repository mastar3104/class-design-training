package com.example.demo.application.usecase.employee

import com.example.demo.domain.entity.Employee
import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.TeamId
import org.springframework.stereotype.Component

@Component
class CreateUseCase(
    private val employeeRepository: EmployeeRepository
) {
    fun execute(
        name: String,
        teamId: TeamId?,
        clubId: ClubId?,
    ) {
        val (employee, employeeChangeClubEvent) = Employee.create(
            name = name,
            teamId = teamId,
            clubId = clubId,
        )

        employeeRepository.save(
            employee = employee,
            employeeChangeClubEvent = employeeChangeClubEvent,
        )
    }
}