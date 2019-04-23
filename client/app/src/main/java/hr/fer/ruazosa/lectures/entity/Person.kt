package hr.fer.tel.ruazosa.lectures.entity

import java.io.Serializable

data class Person(
        var id: Long? = null,
        var firstName: String = "",
        var lastName: String = "",
        var room: String? = null,
        var phone: String? = null
) : Serializable
