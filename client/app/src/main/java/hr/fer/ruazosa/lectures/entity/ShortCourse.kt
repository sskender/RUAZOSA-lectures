package hr.fer.tel.ruazosa.lectures.entity

import java.io.Serializable

data class ShortCourse(
        var id: Long? = null,
        var name: String = ""
) : Serializable {
    constructor(c: Course) : this(
            c.id,
            c.name
    )

    override fun toString(): String {
        return name
    }
}
