package hr.fer.tel.ruazosa.lectures.net.retrofit


import hr.fer.tel.ruazosa.lectures.entity.Course
import hr.fer.tel.ruazosa.lectures.entity.ShortCourse
import hr.fer.tel.ruazosa.lectures.entity.ShortPerson
import retrofit.http.GET
import retrofit.http.Path

interface LecturesService {
    @get:GET("/courses")
    val listOfCourses: List<ShortCourse>

    @GET("/courses/{id}")
    fun getCourse(@Path("id") id: Long?): Course

    @GET("/courses/{id}/students")
    fun getCourseStudents(@Path("id") courseId: Long?): List<ShortPerson>
}
