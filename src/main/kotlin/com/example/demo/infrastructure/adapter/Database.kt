package com.example.demo.infrastructure.adapter

import org.springframework.stereotype.Component

@Component
class Database {

    companion object {
        val defaultEmployee = listOf(
            mapOf(
                "id" to "emp-0001",
                "name" to "Yamada Taro",
                "teamId" to "tm-0001",
                "clubId" to "clb-0001",
                "clubApproval" to "true",
            ),
            mapOf(
                "id" to "emp-0002",
                "name" to "Suzuki Ichiro",
                "teamId" to "tm-0001",
                "clubId" to "clb-0001",
                "clubApproval" to "false",
            ),
            mapOf(
                "id" to "emp-0003",
                "name" to "Tanaka Hanako",
                "teamId" to "tm-0002",
                "clubId" to "clb-0002",
                "clubApproval" to "true",
            ),
            mapOf(
                "id" to "emp-0004",
                "name" to "Takahashi Jiro",
                "teamId" to "tm-0002",
                "clubId" to "clb-0000",
                "clubApproval" to "false",
            ),
        )

        val defaultClubs = listOf(
            mapOf(
                "id" to "clb-0000",
                "name" to "未所属",
                "numberOfEmployee" to "1",
            ),
            mapOf(
                "id" to "clb-0001",
                "name" to "サッカー部",
                "numberOfEmployee" to "2",
            ),
            mapOf(
                "id" to "clb-0002",
                "name" to "野球部",
                "numberOfEmployee" to "1",
            )
        )
    }

    private var _employees = defaultEmployee.toMutableList()
    private var _clubs = defaultClubs.toMutableList()

    val employees
        get() = _employees

    val clubs
        get() = _clubs

    fun init() {
        _employees = defaultEmployee.toMutableList()
        _clubs = defaultClubs.toMutableList()
    }

    fun employeesAdd(resource: Map<String, String>) {
        val index = employees.indexOfFirst { it["id"] == resource["id"] }
        if (index == -1) {
            _employees.add(resource)
        } else {
            _employees[index] = resource
        }
    }

    fun clubAdd(resource: Map<String, String>) {
        val index = clubs.indexOfFirst { it["id"] == resource["id"] }
        if (index == -1) {
            _clubs.add(resource)
        } else {
            _clubs[index] = resource
        }
    }

    fun clubAddNumberOfEmployee(id: String) {
        val index = clubs.indexOfFirst { it["id"] == id }
        if (index != -1) {
            _clubs[index] = mapOf(
                "id" to _clubs[index]["id"]!!,
                "name" to _clubs[index]["name"]!!,
                "numberOfEmployee" to (_clubs[index]["numberOfEmployee"]!!.toInt() + 1).toString(),
            )
        }
    }

    fun clubSubNumberOfEmployee(id: String) {
        val index = clubs.indexOfFirst { it["id"] == id }
        if (index != -1) {
            _clubs[index] = mapOf(
                "id" to _clubs[index]["id"]!!,
                "name" to _clubs[index]["name"]!!,
                "numberOfEmployee" to (_clubs[index]["numberOfEmployee"]!!.toInt() - 1).toString(),
            )
        }
    }
}