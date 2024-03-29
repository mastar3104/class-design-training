package com.example.demo.domain.service

import com.example.demo.domain.entity.Employee
import com.example.demo.domain.value.ClubId
import org.springframework.stereotype.Service

@Service
class ClubChangeService {

    fun change(employee: Employee, newClubId: ClubId): Employee {
        if (employee.clubApproval.not()) {
            throw IllegalArgumentException("未認証状態ではクラブの変更はできません。")
        }
        return Employee(
            id = employee.id,
            name = employee.name,
            teamId = employee.teamId,
            clubId = newClubId,
            clubApproval = false,
        )
    }

    fun join(employee: Employee, newClubId: ClubId): Employee {
        if (employee.clubId.isNotBelongId.not()) {
            throw IllegalArgumentException("すでにクラブに参加しています。")
        }
        if (newClubId.isNotBelongId) {
            throw IllegalArgumentException("未所属クラブに新規参加することはできません。")
        }
        return Employee(
            id = employee.id,
            name = employee.name,
            teamId = employee.teamId,
            clubId = newClubId,
            clubApproval = false,
        )
    }
}