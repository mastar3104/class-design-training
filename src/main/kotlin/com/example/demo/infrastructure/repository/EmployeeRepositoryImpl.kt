package com.example.demo.infrastructure.repository

import com.example.demo.domain.entity.Employee
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
            Employee(
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

    override fun save(employee: Employee) {
        val resource = mapOf(
            "id" to employee.id.toString(),
            "name" to employee.name,
            "teamId" to employee.teamId.toString(),
            "clubId" to employee.clubId.toString(),
            "clubApproval" to if (employee.clubApproval) "true" else "false",
        )
        database.employeesAdd(resource)
    }
}