package controllers

import models.Student

import javax.inject._
import play.api.mvc._
import services.StudentsService
import models.Student._
import play.api.libs.json.Json

@Singleton
class StudentsController @Inject()(
                                    studentsService: StudentsService,
                                    cc: ControllerComponents
                                  ) extends AbstractController(cc) {

  def fetchStudents():Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(studentsService.fetchStudents()))
  }

  def fetchStudentById(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    studentsService.fetchStudentById(id) match {
      case Some(st) => Ok(Json.toJson(st))
      case None => NotFound(s"No student found with id = $id")
    }
  }

  def saveStudent = Action{ request =>
    val json = request.body.asJson.get
    val requestBody = json.as[Student]
    studentsService.saveStudent(requestBody)
    Ok("")
  }

  def updateStudent() = Action { request =>
    val requestBody = request.body.asJson.get
    val student = requestBody.as[Student]
    studentsService.updateStudent(student)
    Ok("")
  }

  def deleteStudent(id: Long)  =  Action { implicit request: Request[AnyContent] =>
    studentsService.deleteStudent(id)
    Ok("")
  }
}
