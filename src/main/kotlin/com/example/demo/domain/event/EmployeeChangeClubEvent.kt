package com.example.demo.domain.event

import com.example.demo.domain.value.ClubId

sealed class EmployeeChangeClubEvent {
    class Change(
        val oldClubId: ClubId,
    ): EmployeeChangeClubEvent()

    object EmployeeCreate: EmployeeChangeClubEvent()
}
