@file:Suppress("NonAsciiCharacters")

package com.example.demo.application

import com.example.demo.infrastructure.adapter.Database
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeClubDeleteTest {

    @Autowired
    private val database: Database? = null

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @BeforeEach
    fun setup() {
        database!!.init()
    }

    @Test
    fun クラブに所属している社員の所属クラブが未所属になること() {
        Assertions.assertEquals(database!!.employees.size, 4)
        Assertions.assertEquals(database.employees[0]["id"], "emp-0001")
        Assertions.assertEquals(database.employees[0]["clubId"], "clb-0001")
        Assertions.assertEquals(database.employees[0]["clubApproval"], "true")

        Assertions.assertEquals(database.clubs.size, 3)
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "1")
        Assertions.assertEquals(database.clubs[1]["id"], "clb-0001")
        Assertions.assertEquals(database.clubs[1]["numberOfEmployee"], "2")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0001/clubs",
            HttpMethod.DELETE,
            null,
            String::class.java,
        )

        Assertions.assertEquals(response.statusCode, HttpStatus.OK)
        Assertions.assertEquals(database.employees.size, 4)
        Assertions.assertEquals(database.employees[0]["clubId"], "clb-0000")
        // クラブが未承認状態となっていること
        Assertions.assertEquals(database.employees[0]["clubApproval"], "false")

        // クラブの社員数が変更されていること
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "2")
        Assertions.assertEquals(database.clubs[1]["id"], "clb-0001")
        Assertions.assertEquals(database.clubs[1]["numberOfEmployee"], "1")
    }

    @Test
    fun 未認証の社員でも所属クラブが未所属に変更できること() {
        Assertions.assertEquals(database!!.employees.size, 4)
        Assertions.assertEquals(database.employees[1]["id"], "emp-0002")
        Assertions.assertEquals(database.employees[1]["clubId"], "clb-0001")
        Assertions.assertEquals(database.employees[1]["clubApproval"], "false")

        Assertions.assertEquals(database.clubs.size, 3)
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "1")
        Assertions.assertEquals(database.clubs[1]["id"], "clb-0001")
        Assertions.assertEquals(database.clubs[1]["numberOfEmployee"], "2")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0002/clubs",
            HttpMethod.DELETE,
            null,
            String::class.java,
        )

        Assertions.assertEquals(response.statusCode, HttpStatus.OK)
        Assertions.assertEquals(database.employees.size, 4)
        Assertions.assertEquals(database.employees[1]["clubId"], "clb-0000")
        // クラブが未承認状態となっていること
        Assertions.assertEquals(database.employees[1]["clubApproval"], "false")

        // クラブの社員数が変更されていること
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "2")
        Assertions.assertEquals(database.clubs[1]["id"], "clb-0001")
        Assertions.assertEquals(database.clubs[1]["numberOfEmployee"], "1")
    }

    @Test
    fun クラブ未所属の社員は変更できないこと() {
        Assertions.assertEquals(database!!.employees.size, 4)
        Assertions.assertEquals(database.employees[3]["id"], "emp-0004")
        Assertions.assertEquals(database.employees[3]["clubId"], "clb-0000")
        Assertions.assertEquals(database.employees[3]["clubApproval"], "false")

        Assertions.assertEquals(database.clubs.size, 3)
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "1")

        val response = testRestTemplate!!.exchange(
            "/employees/emp-0004/clubs",
            HttpMethod.DELETE,
            null,
            String::class.java,
        )

        Assertions.assertEquals(response.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)
        Assertions.assertEquals(database.employees.size, 4)
        Assertions.assertEquals(database.employees[3]["clubId"], "clb-0000")
        Assertions.assertEquals(database.employees[3]["clubApproval"], "false")

        // クラブの社員数が変更されていること
        Assertions.assertEquals(database.clubs[0]["id"], "clb-0000")
        Assertions.assertEquals(database.clubs[0]["numberOfEmployee"], "1")
    }


}