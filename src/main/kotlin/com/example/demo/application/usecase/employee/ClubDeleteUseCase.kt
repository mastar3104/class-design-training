package com.example.demo.application.usecase.employee

import com.example.demo.domain.entity.Club
import com.example.demo.domain.entity.Employee
import com.example.demo.domain.repository.ClubRepository
import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import org.springframework.stereotype.Component

@Component
class ClubDeleteUseCase(
    private val employeeRepository: EmployeeRepository,
    private val clubRepository: ClubRepository,
) {
    fun execute(
        employeeId: EmployeeId,
    ) {
        val employee = employeeRepository.find(employeeId) ?: throw IllegalArgumentException("該当の社員情報はありません。 社員ID:$employeeId")
        if (employee.clubId.isNotBelongId) {
            throw IllegalArgumentException("クラブ未所属の社員は、クラブ脱退の対象外です。")
        }

        val oldClub = clubRepository.find(employee.clubId) ?: throw IllegalStateException("社員情報にひもづくクラブが存在しません。 クラブID:${employee.clubId}")
        val newClub = clubRepository.find(ClubId.NOT_BELONG) ?: throw IllegalArgumentException("該当のクラブ情報はありません。 クラブID:${ClubId.NOT_BELONG}")

        val newEmployee = Employee(
            id = employee.id,
            name = employee.name,
            teamId = employee.teamId,
            clubId = ClubId.NOT_BELONG,
            clubApproval = employee.clubApproval,
        )

        employeeRepository.save(newEmployee)

        val updateOldClub = Club(
            id = oldClub.id,
            name = oldClub.name,
            numberOfEmployee = oldClub.numberOfEmployee - 1,
        )
        clubRepository.save(updateOldClub)

        val updateNewClub = Club(
            id = newClub.id,
            name = newClub.name,
            numberOfEmployee = newClub.numberOfEmployee + 1,
        )
        clubRepository.save(updateNewClub)
    }
}