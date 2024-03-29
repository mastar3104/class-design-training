package com.example.demo.domain.repository

import com.example.demo.domain.entity.Employee
import com.example.demo.domain.value.EmployeeId

interface EmployeeRepository {
    fun find(employeeId: EmployeeId): Employee?

    fun save(employee: Employee)
}