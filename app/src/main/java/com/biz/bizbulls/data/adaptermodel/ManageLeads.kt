package com.biz.bizbulls.data.adaptermodel

import java.util.ArrayList

class ManageLeads(var location: String, employees: List<Employee>) {
    var employees: List<Employee> = ArrayList()

    init {
        this.employees = employees
    }
}