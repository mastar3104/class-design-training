package com.example.demo.domain.value

data class TeamId(
    private val value: String
) {
    override fun toString(): String {
        return value
    }

    companion object {
        // 入社直後の未配属状態を示すID
        val DEFAULT = TeamId("tm-0000")
    }
}
