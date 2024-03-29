@file:Suppress("NonAsciiCharacters")

package com.example.demo.application

import com.example.demo.infrastructure.adapter.Database
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeCreateTest {

    @Autowired
    private val database: Database? = null

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @Test
    fun 社員作成のテスト() {

        testRestTemplate!!.postForEntity(
            "/employee/", mapOf("name" to "test"), String::class.java
        )

        assertEquals(database!!.employees.size, 5)
        assertEquals(database.employees[4]["name"], "test")
        assertEquals(database.employees[4]["teamId"], "tm-0000")
        assertEquals(database.employees[4]["clubId"], "clb-0000")
        assertEquals(database.employees[4]["clubApproval"], "false")
    }

}