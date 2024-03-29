package com.example.demo.application.usecase.employee

import com.example.demo.domain.repository.ClubRepository
import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import org.springframework.stereotype.Component

@Component
class ClubChangeUseCase(
    private val employeeRepository: EmployeeRepository,
    private val clubRepository: ClubRepository,
) {
    fun execute(
        employeeId: EmployeeId,
        newClubId: ClubId,
    ) {
        if (newClubId.isNotBelongId) {
            throw IllegalArgumentException("未所属へは変更できません。未所属に紐付けたい場合はクラブ削除を利用してください。")
        }

        clubRepository.find(newClubId) ?: throw IllegalArgumentException("該当のクラブ情報はありません。 クラブID:${newClubId}")
        val employee = employeeRepository.find(employeeId) ?: throw IllegalArgumentException("該当の社員情報はありません。 社員ID:$employeeId")

        val (newEmployee, employeeChangeClubEvent) = employee.joinClub(newClubId)

        employeeRepository.save(
            employee = newEmployee,
            employeeChangeClubEvent = employeeChangeClubEvent,
        )
    }
}