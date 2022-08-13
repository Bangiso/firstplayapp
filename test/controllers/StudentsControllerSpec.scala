package controllers

import models.Student
import org.mockito.Mockito.when
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import services.StudentsService
import org.scalatestplus.mockito.MockitoSugar.mock
import play.api.Play.materializer

class StudentsControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "StudentsController GET" should {

    "fetch students from a new instance of controller" in {
      val studentsService  = mock[StudentsService]

      when(studentsService.fetchStudents()).thenReturn(List(
        new Student(Some(1), "Sam", 78.8),
      ))


      val controller = new StudentsController(studentsService,stubControllerComponents())
      val home = controller.fetchStudents().apply(FakeRequest(GET, "/api/students"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include("""[{"id":1,"name":"Sam","gpa":78.8}]""")
    }
    "fetch students from the application" in {

      val controller = inject[StudentsController]
      val home = controller.fetchStudents().apply(FakeRequest(GET, "/api/students"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
    }

    "fetch student by id from a new instance of controller" in {
      val studentsService  = mock[StudentsService]

      when(studentsService.fetchStudentById(2)).thenReturn(Some(
        new Student(Some(2), "Will", 87.4)
      ))


      val controller = new StudentsController(studentsService,stubControllerComponents())
      val home = controller.fetchStudentById(Some(2)).apply(FakeRequest(GET, "/api/students/2"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include("""{"id":2,"name":"Will","gpa":87.4}""")
    }

    "fetch student by id for invalid id" in {
      val studentsService  = mock[StudentsService]

      when(studentsService.fetchStudentById(4)).thenReturn(None)

      val controller = new StudentsController(studentsService,stubControllerComponents())
      val home = controller.fetchStudentById(Some(4)).apply(FakeRequest(GET, "/api/students/4"))

      status(home) mustBe NOT_FOUND
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("No student found with id = 4")
    }
  }
}
