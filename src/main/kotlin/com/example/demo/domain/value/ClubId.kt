package com.example.demo.domain.value

data class ClubId(
    private val value: String
) {
    override fun toString(): String {
        return value
    }

    companion object {
        // 未所属を示すID
        val NOT_BELONG = ClubId("clb-0000")
    }

    val isNotBelongId = this == NOT_BELONG
}
