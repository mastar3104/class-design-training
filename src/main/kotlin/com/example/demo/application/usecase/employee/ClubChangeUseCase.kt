package com.example.demo.application.usecase.employee

import com.example.demo.domain.entity.Club
import com.example.demo.domain.repository.ClubRepository
import com.example.demo.domain.repository.EmployeeRepository
import com.example.demo.domain.service.ClubChangeService
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import org.springframework.stereotype.Component

@Component
class ClubChangeUseCase(
    private val employeeRepository: EmployeeRepository,
    private val clubRepository: ClubRepository,
    private val clubChangeService: ClubChangeService,
) {
    fun execute(
        employeeId: EmployeeId,
        newClubId: ClubId,
    ) {
        if (newClubId.isNotBelongId) {
            throw IllegalArgumentException("未所属へは変更できません。未所属に紐付けたい場合はクラブ削除を利用してください。")
        }

        val employee = employeeRepository.find(employeeId) ?: throw IllegalArgumentException("該当の社員情報はありません。 社員ID:$employeeId")
        val oldClub = clubRepository.find(employee.clubId) ?: throw IllegalStateException("社員情報にひもづくクラブが存在しません。 クラブID:${employee.clubId}")
        val newClub = clubRepository.find(newClubId) ?: throw IllegalArgumentException("該当のクラブ情報はありません。 クラブID:${newClubId}")

        val newEmployee = if (employee.clubId.isNotBelongId) {
            clubChangeService.join(
                employee = employee,
                newClubId = newClubId,
            )
        } else {
            clubChangeService.change(
                employee = employee,
                newClubId = newClubId,
            )
        }

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