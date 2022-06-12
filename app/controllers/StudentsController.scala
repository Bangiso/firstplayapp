package controllers

import models.Search.searchForm
import models.Student

import javax.inject._
import play.api.mvc._
import services.StudentsService
import models.Student._

import java.sql.SQLIntegrityConstraintViolationException

@Singleton
class StudentsController @Inject()(
                                    studentsService: StudentsService,
                                    cc: ControllerComponents
                                  ) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def fetchStudents(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.students(studentsService.fetchStudents()))
  }

  def fetchStudentById(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    studentsService.fetchStudentById(id) match {
      case Some(student) => Ok(views.html.studentById(student, studentForm))
      case None => NotFound(s"No student found with id = $id")
    }
  }

  def saveStudent() = Action(parse.form(studentForm)) { implicit request =>
    val studentData = request.body
    val newStudent = models.Student(studentData.id, studentData.name, studentData.gpa)
    try {
      studentsService.saveStudent(newStudent)
      Redirect(routes.StudentsController.fetchStudents)
    }
    catch {
      case _: SQLIntegrityConstraintViolationException => Conflict(views.html.error(s"Student with id = ${newStudent.id} already exists"))
    }

  }

  def search() = Action(parse.form(searchForm)) { implicit request =>
    val keyword = request.body.keyword
    if (keyword != null & keyword != "") {
      val students = studentsService.fetchStudents().filter(_.name.toLowerCase().contains(keyword.toLowerCase()))
      if (students.isEmpty) {
        NotFound(views.html.error(s"No student found with name $keyword"))
      } else {
        Ok(views.html.students(students))
      }
    } else {
      Ok(views.html.error("Invalid input."))
    }
  }

  def createStudentForm = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.createStudent(studentForm))
  }


  def updateStudent() = Action { request =>
    val requestBody = request.body.asJson.get
    val student = requestBody.as[Student]
    studentsService.updateStudent(student)
    Ok("")
  }

  def deleteStudent(id: Long) = Action { implicit request: Request[AnyContent] =>
    studentsService.deleteStudent(id)
    Ok("")
  }

  def getAll(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val students = studentsService.fetchStudents()
    if (students.isEmpty) {
      NotFound("No students found")
    } else {
      Ok(views.html.index(students))
    }
  }
}
