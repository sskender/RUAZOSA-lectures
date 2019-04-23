package hr.fer.tel.ruazosa.lectures.entity

import java.io.Serializable

data class Course(
        var id: Long? = null,
        var name: String = "",
        var description: String = "",
        var teacher: ShortPerson? = null
) : Serializable
