package com.example.admin_stenli

import java.util.*

class UserModel(
    var id: Int =  getAutoId(),
    var name: String = "",
    var email: String = ""
) {
    companion object {
        fun getAutoId() : Int {
            val random = Random()
            return random.nextInt(1000)
        }
    }


}