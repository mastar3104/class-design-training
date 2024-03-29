package com.example.demo.domain.value

import java.util.UUID

data class EmployeeId(
    private val value: String
) {
    override fun toString(): String {
        return value
    }

    companion object {
        fun create(): EmployeeId {
            return EmployeeId(UUID.randomUUID().toString())
        }
    }
}