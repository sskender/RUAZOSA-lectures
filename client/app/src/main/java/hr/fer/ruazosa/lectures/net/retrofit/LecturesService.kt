package hr.fer.tel.ruazosa.lectures.net.retrofit


import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.Person
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import retrofit.http.*
import java.lang.Void as Void1
import kotlin.Unit as Unit1

interface LecturesService {
    // courses
    @get:GET("/courses")
    val listOfCourses: List<ShortCourse>

    @GET("/courses/{id}")
    fun getCourse(@Path("id") id: Long?): Course

    @GET("/courses/{id}/students")
    fun getCourseStudents(@Path("id") courseId: Long?): List<ShortPerson>

    // persons
    @get:GET("/persons")
    val listOfPersons: List<ShortPerson>

    @GET("/persons/{id}")
    fun getPerson(@Path("id") id: Long?): Person

    @DELETE("/persons/{id}")
    fun deletePerson(@Path("id") id: Long?): Boolean?

    @POST("/persons")
    fun postPerson(@Body person: Person?): Boolean?

    // enroll and unroll
    @POST("/courses/{cid}/enrollPerson/{pid}")
    fun enrollPersonToCourse(@Path("cid") courseId: Long?, @Path("pid") PersonId: Long?): Boolean?

    @POST("/courses/{cid}/unenrollPerson/{pid}")
    fun disenrollPersonFromCourse(@Path("pid") personId: Long?, @Path("cid") courseId: Long?): Boolean?
}
