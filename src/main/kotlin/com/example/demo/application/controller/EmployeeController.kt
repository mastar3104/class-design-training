package com.example.demo.application.controller

import com.example.demo.application.form.employee.CreateBody
import com.example.demo.application.usecase.employee.ClubChangeUseCase
import com.example.demo.application.usecase.employee.ClubDeleteUseCase
import com.example.demo.application.usecase.employee.CreateUseCase
import com.example.demo.domain.value.ClubId
import com.example.demo.domain.value.EmployeeId
import com.example.demo.domain.value.TeamId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class EmployeeController(
    private val createUseCase: CreateUseCase,
    private val clubChangeUseCase: ClubChangeUseCase,
    private val clubDeleteUseCase: ClubDeleteUseCase,
) {

    @PostMapping("/employee/")
    fun create(
        @RequestBody requestBody: CreateBody
    ) {
        createUseCase.execute(
            name = requestBody.name,
            teamId = requestBody.teamId?.let { TeamId(it) },
            clubId = requestBody.clubId?.let { ClubId(it) }
        )
    }

    @PutMapping("/employees/{employeeId}/clubs/{clubId}")
    fun clubChange(
        @PathVariable employeeId: String,
        @PathVariable clubId: String,
    ) {
        clubChangeUseCase.execute(
            employeeId = EmployeeId(employeeId),
            newClubId = ClubId(clubId),
        )
    }

    @DeleteMapping("/employees/{employeeId}/clubs")
    fun clubDelete(
        @PathVariable employeeId: String,
    ) {
        clubDeleteUseCase.execute(
            employeeId = EmployeeId(employeeId),
        )
    }
}