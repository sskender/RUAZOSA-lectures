package hr.fer.tel.ruazosa.lectures.net.resttemplate

import android.util.Log
import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import hr.fer.tel.ruazosa.lectures.net.RestFactory
import hr.fer.tel.ruazosa.lectures.net.RestInterface
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.util.*

class RestSpringTemplate : RestInterface {
    private val baseURL: String = "http://" + RestFactory.BASE_IP + ":8080/api"

    private val restTemplate = RestTemplate()

    init {
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
    }

    override fun getListOfCourses(): List<ShortCourse>? {
        try {
            return this.restTemplate.getForObject("${baseURL}/courses", ShortCourseList::class.java)
        } catch (e: Exception) {
            Log.i("RUAZOSA", e.message, e)
        }

        return LinkedList()
    }

    class ShortCourseList : LinkedList<ShortCourse>()

    override fun getListOfPersons(): List<ShortPerson>? {
        throw UnsupportedOperationException()
    }

    override fun getCourse(id: Long?): Course? {
        try {
            return restTemplate.getForObject("${baseURL}/courses/$id", Course::class.java)
        } catch (e: Exception) {
            Log.i("RUAZOSA", e.message, e)
        }

        return null
    }

    override fun getCourseStudents(courseId: Long?): List<ShortPerson>? {
        try {
            return restTemplate.getForObject("${baseURL}/courses/$courseId/students", ShortPersonList::class.java)
        } catch (e: Exception) {
            Log.i("RUAZOSA", e.message, e)
        }

        return null
    }

    class ShortPersonList : LinkedList<ShortPerson>()

    override fun getPerson(id: Long?): Person? {
        throw UnsupportedOperationException()
    }

    override fun enrollPersonToCourse(personId: Long?, courseId: Long?): Boolean? {
        throw UnsupportedOperationException()
    }

    override fun disenrollPersonFromCourse(personId: Long?, courseId: Long?): Boolean? {
        throw UnsupportedOperationException()
    }

    override fun deletePerson(id: Long?): Boolean? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
