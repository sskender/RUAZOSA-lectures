package hr.fer.tel.ruazosa.lectures.net.inmemory

import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestInterface
import java.util.*

class InMemoryRestImplementation : RestInterface {
    private val persons: MutableList<Person>
    private val courses: MutableList<Course>
    private val enrolled: MutableMap<Long, MutableList<Person>> // CourseId, list of students

    init {
        persons = LinkedList()
        courses = LinkedList()
        enrolled = HashMap()

        var c = Course(1L, "RUAZOSA", "Razvoj usluga i aplikacija za operacijski sustav Android")
        courses.add(c)

        c = Course(2L, "KP", "Konkurentno programiranje")
        courses.add(c)

        c = Course(3L, "RASSUS", "Raspodijeljeni sustavi")
        courses.add(c)

        c = Course(4L, "OOP", "Objektno orijentirano programiranje")
        courses.add(c)

        var p = Person(1L, "Pero", "Perić", "1111", "C15-15")
        persons.add(p)
        getCourse(1L)?.teacher = ShortPerson(p)

        p = Person(2L, "Iva", "Ivić", "555", "C0-32")
        persons.add(p)
        enrollPersonToCourse(p.id, c.id)

        p = Person(3L, "Jura", "Jurić", "333", "C3-81")
        persons.add(p)
        enrollPersonToCourse(p.id, c.id)
    }

    override fun getListOfCourses(): List<ShortCourse>? {
            val result = LinkedList<ShortCourse>()
            for (c in courses)
                result.add(ShortCourse(c))

            return result
        }

    override fun getCourse(id: Long?): Course? {
        for (c in courses)
            if (c.id == id) return c
        return null
    }

    override fun getCourseStudents(courseId: Long?): List<ShortPerson> {
        val result = LinkedList<ShortPerson>()
        val enrolledPersonList = enrolled[courseId]
        if (enrolledPersonList != null) {
            for (p in enrolledPersonList)
                result.add(ShortPerson(p))
        }

        return result
    }

    override fun getListOfPersons(): List<ShortPerson>? {
            val result = LinkedList<ShortPerson>()
            for (p in persons)
                result.add(ShortPerson(p))
            return result
    }

    override fun getPerson(id: Long?): Person? {
        for (p in persons)
            if (p.id == id) return p
        return null
    }

    override fun enrollPersonToCourse(personId: Long?, courseId: Long?): Boolean? {
        if(courseId != null && personId != null) {
            val peronsEnrolled = enrolled.get(courseId)
            val person = getPerson(personId)
            if (person != null && peronsEnrolled != null) {
                peronsEnrolled.add(person)
                return true
            }
            return null
        }

        return false
    }

    override fun disenrollPersonFromCourse(personId: Long?, courseId: Long?): Boolean? {
        val peronsEnrolled = enrolled[courseId] ?: return true

        for (p in peronsEnrolled) {
            if (p.id == personId)
                peronsEnrolled.remove(p)
        }
        return true
    }

    override fun deletePerson(id: Long?): Boolean? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
