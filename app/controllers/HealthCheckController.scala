package controllers

import javax.inject._
import play.api.mvc._
import services.StudentsService

@Singleton
class HealthCheckController @Inject()(studentsService: StudentsService, cc: ControllerComponents) extends AbstractController(cc) {
  def healthCheck() = Action { implicit request: Request[AnyContent]  =>
    studentsService.healthCheck()
    Ok("")
  }
}
