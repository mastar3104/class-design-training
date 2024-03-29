package com.example.demo.infrastructure.repository

import com.example.demo.domain.entity.Employee
import com.example.demo.domain.event.EmployeeChangeClubEvent
import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import com.example.demo.domain.value.TeamId
import com.example.demo.infrastructure.adapter.Database
import org.springframework.stereotype.Repository

@Repository
class EmployeeRepositoryImpl(
    private val database: Database,
): EmployeeRepository {
    override fun find(employeeId: EmployeeId): Employee? {
        return database.employees.find { it["id"] == employeeId.toString() }?.let { resource ->
            Employee.reconstructor(
                id = employeeId,
                name = resource["name"]!!,
                teamId = TeamId(resource["teamId"]!!),
                clubId = ClubId(resource["clubId"]!!),
                clubApproval = when(resource["clubApproval"]!!) {
                    "true" -> true
                    else -> false
                }
            )
        }
    }

    // トランザクショナル
    override fun save(employee: Employee, employeeChangeClubEvent: EmployeeChangeClubEvent) {
        val resource = mapOf(
            "id" to employee.id.toString(),
            "name" to employee.name,
            "teamId" to employee.teamId.toString(),
            "clubId" to employee.clubId.toString(),
            "clubApproval" to if (employee.clubApproval) "true" else "false",
        )
        database.employeesAdd(resource)
        when(employeeChangeClubEvent) {
            is EmployeeChangeClubEvent.Change -> {
                // UPDATE SET numberOfEmployee + 1 WHERE id = :updateOldClubId
                database.clubAddNumberOfEmployee(employee.clubId.toString())
                // UPDATE SET numberOfEmployee - 1 WHERE id = :updateNewClubId
                database.clubSubNumberOfEmployee(employeeChangeClubEvent.oldClubId.toString())
            }
            is EmployeeChangeClubEvent.EmployeeCreate -> {
                // UPDATE SET numberOfEmployee + 1 WHERE id = :updateOldClubId
                database.clubAddNumberOfEmployee(employee.clubId.toString())
            }
        }
    }
}