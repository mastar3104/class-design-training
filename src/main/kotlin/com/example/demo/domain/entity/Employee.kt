package com.example.demo.domain.entity

import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import com.example.demo.domain.value.TeamId

class Employee private constructor(
    val id: EmployeeId,
    val name: String,
    val teamId: TeamId,
    val clubId: ClubId,
    val clubApproval: Boolean
) {
    companion object {

        // RepositoryからEntityを再生成する場合のみ利用
        fun reconstructor(
            id: EmployeeId,
            name: String,
            teamId: TeamId,
            clubId: ClubId,
            clubApproval: Boolean,
        ): Employee {
            return Employee(
                id = id,
                name = name,
                teamId = teamId,
                clubId = clubId,
                clubApproval = clubApproval,
            )
        }

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

    fun deleteClub(): Employee {
        return Employee(
            id = id,
            name = name,
            teamId = teamId,
            clubId = ClubId.NOT_BELONG,
            clubApproval = false,
        )
    }

    fun joinClub(newClubId: ClubId): Employee {
        if (clubId.isNotBelongId) {
            if (newClubId.isNotBelongId) {
                throw IllegalArgumentException("未所属クラブに新規参加することはできません。")
            }
        } else {
            if (clubApproval.not()) {
                throw IllegalArgumentException("未認証状態ではクラブの変更はできません。")
            }
        }

        return Employee(
            id = id,
            name = name,
            teamId = teamId,
            clubId = newClubId,
            clubApproval = false,
        )
    }
}


