package hr.fer.tel.ruazosa.lectures.entity

import java.io.Serializable

data class ShortPerson(
        var id: Long? = null,
        var name: String = ""
) : Serializable {

    constructor(p: Person) : this(
            p.id,
            p.firstName + " " + p.lastName
    )
}
