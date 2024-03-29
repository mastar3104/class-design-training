@file:Suppress("NonAsciiCharacters")

package com.example.demo.application

import com.example.demo.infrastructure.adapter.Database
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeClubChangeTest {

    @Autowired
    private val database: Database? = null

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @BeforeEach
    fun setup() {
        database!!.init()
    }

    @Test
    fun `クラブに所属し、クラブ認証済みの社員は、所属クラブが変更できること`() {
        assertEquals(database!!.employees.size, 4)
        assertEquals(database.employees[0]["id"], "emp-0001")
        assertEquals(database.employees[0]["clubId"], "clb-0001")
        assertEquals(database.employees[0]["clubApproval"], "true")

        assertEquals(database.clubs.size, 3)
        assertEquals(database.clubs[1]["id"], "clb-0001")
        assertEquals(database.clubs[1]["numberOfEmployee"], "2")
        assertEquals(database.clubs[2]["id"], "clb-0002")
        assertEquals(database.clubs[2]["numberOfEmployee"], "1")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0001/clubs/clb-0002",
            HttpMethod.PUT,
            null,
            String::class.java,
        )

        assertEquals(response.statusCode, HttpStatus.OK)
        assertEquals(database.employees.size, 4)
        assertEquals(database.employees[0]["clubId"], "clb-0002")
        // クラブが未承認状態となっていること
        assertEquals(database.employees[0]["clubApproval"], "false")

        // クラブの社員数が変更されていること
        assertEquals(database.clubs[1]["id"], "clb-0001")
        assertEquals(database.clubs[1]["numberOfEmployee"], "1")
        assertEquals(database.clubs[2]["id"], "clb-0002")
        assertEquals(database.clubs[2]["numberOfEmployee"], "2")
    }

    @Test
    fun `クラブに所属していない社員は、クラブ未認証でも所属クラブの変更ができること`() {
        assertEquals(database!!.employees.size, 4)
        assertEquals(database.employees[3]["id"], "emp-0004")
        assertEquals(database.employees[3]["clubId"], "clb-0000")
        assertEquals(database.employees[3]["clubApproval"], "false")

        assertEquals(database.clubs.size, 3)
        assertEquals(database.clubs[0]["id"], "clb-0000")
        assertEquals(database.clubs[0]["numberOfEmployee"], "1")
        assertEquals(database.clubs[1]["id"], "clb-0001")
        assertEquals(database.clubs[1]["numberOfEmployee"], "2")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0004/clubs/clb-0001",
            HttpMethod.PUT,
            null,
            String::class.java,
        )

        assertEquals(response.statusCode, HttpStatus.OK)

        assertEquals(database.employees.size, 4)
        assertEquals(database.employees[3]["id"], "emp-0004")
        assertEquals(database.employees[3]["clubId"], "clb-0001")
        assertEquals(database.employees[3]["clubApproval"], "false")

        // クラブの社員数が変更されていること
        assertEquals(database.clubs[0]["id"], "clb-0000")
        assertEquals(database.clubs[0]["numberOfEmployee"], "0")
        assertEquals(database.clubs[1]["id"], "clb-0001")
        assertEquals(database.clubs[1]["numberOfEmployee"], "3")

    }

    @Test
    fun `未所属IDに更新しようとした場合、エラーになること`() {
        val response = testRestTemplate!!.exchange(
            "/employees/emp-0001/clubs/clb-0000",
            HttpMethod.PUT,
            null,
            String::class.java,
        )

        assertEquals(response.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)

        assertEquals(database!!.employees.size, 4)
        assertEquals(database.employees[0]["clubId"], "clb-0001")

    }

    @Test
    fun クラブが未認証の社員は所属クラブの変更ができないこと() {
        assertEquals(database!!.employees.size, 4)
        assertEquals(database.employees[1]["id"], "emp-0002")
        assertEquals(database.employees[1]["clubId"], "clb-0001")
        assertEquals(database.employees[1]["clubApproval"], "false")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0002/clubs/clb-0002",
            HttpMethod.PUT,
            null,
            String::class.java,
        )

        assertEquals(response.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)

        assertEquals(database.employees.size, 4)
        assertEquals(database.employees[1]["id"], "emp-0002")
        assertEquals(database.employees[1]["clubId"], "clb-0001")
        assertEquals(database.employees[1]["clubApproval"], "false")
    }

}