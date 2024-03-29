package com.example.demo.domain.entity

import com.example.demo.domain.value.ClubId

class Club private constructor(
    val id: ClubId,
    val name: String,
    val numberOfEmployee: Int,
) {
    companion object {
        fun reconstructor(
            id: ClubId,
            name: String,
            numberOfEmployee: Int,
        ): Club {
            return Club(
                id = id,
                name = name,
                numberOfEmployee = numberOfEmployee,
            )
        }
    }

    fun joinEmployee(): Club {
        return Club(
            id = id,
            name = name,
            numberOfEmployee = numberOfEmployee + 1,
        )
    }

    fun leaveEmployee(): Club {
        return Club(
            id = id,
            name = name,
            numberOfEmployee = numberOfEmployee - 1,
        )
    }
}