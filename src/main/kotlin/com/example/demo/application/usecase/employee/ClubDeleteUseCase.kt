package com.example.demo.application.usecase.employee

import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.value.EmployeeId
import org.springframework.stereotype.Component

@Component
class ClubDeleteUseCase(
    private val employeeRepository: EmployeeRepository,
) {
    fun execute(
        employeeId: EmployeeId,
    ) {
        val employee = employeeRepository.find(employeeId) ?: throw IllegalArgumentException("該当の社員情報はありません。 社員ID:$employeeId")
        if (employee.clubId.isNotBelongId) {
            throw IllegalArgumentException("クラブ未所属の社員は、クラブ脱退の対象外です。")
        }
        val (newEmployee, employeeChangeClubEvent) = employee.deleteClub()

        employeeRepository.save(
            employee = newEmployee,
            employeeChangeClubEvent = employeeChangeClubEvent,
        )
    }
}